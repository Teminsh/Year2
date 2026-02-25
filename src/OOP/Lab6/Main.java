package OOP.Lab6;//region Task
/*
Лабораторная работа 6 (виртуальная клавиатура)

Создать класс виртуальной клавиутуры, которая поддерживает:
- добавление/изменение ассоциации клавиши/комбинации клавиш с командой, которую она выполняет
- откат последней выполненной команды (операция undo)
- возврат последней выполненной команды после выполнения отката (операция redo)
- сохренения добавленных ассоциаций на жесткий диск и восстановление при перезапуске программы

Реализовать класс, используя паттерн Command. При этом реализовать комманды:
- команда, которая печатает символ (сделать класс, который обрабатывает все символы, а не много классов под каждый символ), при этом в случае отмены действия стирает последний выведенный символ
(можно реализовать курс вправо, напечать пробел, курсор вправо)
- команда, которая симулирует увеличение звука (реальную работу со звуком делать не нужно, а просто выводить информацию в текстовом режиме)
- команда, которая симулирует уменьшение звука (реальную работу со звуком делать не нужно, а просто выводить информацию в текстовом режиме)
- команда, которая симулирует запуск медиа плеера (реальную работу с плеером делать не нужно, а просто выводить информацию в текстовом режиме)
- можно добавить что-то свое

Сделать сохранение состояния клавиатуры, используя модифицированный паттерн Memento (то есть за сохранение/восстановление ассоциаций должен отвечать отдельный класс, а не класс клавиатуры).
При этом реализовать и использовать общую схему для сериализации по примеру разобранному на практике:
- не заависит от формата (но для примера реализации рекомендую использовать формат json (но можно и xml, bson, yaml, и даже для нестандартно мыслящих личностей csv или xslx))
- поддерживает тонкую натройку процесса сериализации (переименование полей, пропуск части полей)
- механизм сериализации и десираилизации в отдельногм классе
- механизм представления класса в виде словря также в отдельном классе

Вывод работы программы сделать либо в консоль либо в текстовый файл в подобном  виде (это приер, его повторять необязательно):
CONSOLE:                                                    TEXT FILE:
a                                                                    a
b                                                                    ab
c                                                                    abc
undo                                                             ab
undo                                                             a
redo                                                              ab
ctrl++                                                            volume increased +20%
ctrl+-                                                             volume decreased +20%
ctrl+p                                                           media player launched
d                                                                   abd
undo                                                            ab
undo                                                            media player closed

Работу приложение выводить в текстовый файл и в консоль

То есть у вас будет примерно  следующий набор классов:
Keyboard, KeyCommand, VolumeUpCommand, VolumeDownCommand, MediaPlayerCommand, KeybordStateSaver

Создавать графический интерфейс необходимости нет, но если кто-то хочет, я ничего против не имею...
Обратите внимание, что стирание истории != откат действия назад
Обратите внимание, что классы команд не должны зависеть от класса виртуальной клавиатуры
 */
//endregion Task

import OOP.Lab6.commands.CommandRegistry;
import OOP.Lab6.io.DualOutput;
import OOP.Lab6.receivers.MediaPlayer;
import OOP.Lab6.receivers.TextBuffer;
import OOP.Lab6.receivers.VolumeSystem;
import OOP.Lab6.serialization.KeyboardMementoMapper;
import OOP.Lab6.serialization.TextSerializer;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Path outFile = Path.of("output.txt");
        Path stateFile = Path.of("keyboard_state.txt");

        try (DualOutput output = new DualOutput(outFile)) {
            TextBuffer textBuffer = new TextBuffer();
            VolumeSystem volume = new VolumeSystem(20);
            MediaPlayer mediaPlayer = new MediaPlayer();

            CommandRegistry registry = new CommandRegistry();
            registry.register("printChar", p -> new OOP.Lab6.commands.PrintCharCommand(textBuffer, getChar(p, "ch")));
            registry.register("volumeUp", p -> new OOP.Lab6.commands.VolumeUpCommand(volume));
            registry.register("volumeDown", p -> new OOP.Lab6.commands.VolumeDownCommand(volume));
            registry.register("mediaPlayer", p -> new OOP.Lab6.commands.MediaPlayerCommand(mediaPlayer));

            Keyboard keyboard = new Keyboard(registry, output);
            KeyboardStateSaver saver = new KeyboardStateSaver(
                    stateFile,
                    new TextSerializer(),
                    new KeyboardMementoMapper()
            );

            saver.tryLoadInto(keyboard, output);
            runInteractiveLoop(keyboard, registry, output);
            saver.saveFrom(keyboard);

        } catch (Exception e) {
            System.out.println("FATAL ERROR: " + e.getMessage());
        }
    }

    //region loop
    private static void runInteractiveLoop(Keyboard keyboard, CommandRegistry registry, DualOutput output) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Keyboard started. Type 'bind <key> <cmd> [args]', 'undo', 'redo', a key to press, or 'exit'.");

        while (true) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("exit")) break;
            if (line.isEmpty()) continue;

            try {
                if (line.equals("undo")) {
                    keyboard.undo();
                } else if (line.equals("redo")) {
                    keyboard.redo();
                } else if (line.startsWith("bind ")) {
                    handleBind(line, keyboard, registry);
                } else {
                    keyboard.press(line);
                }
            } catch (IllegalArgumentException e) {
                output.log("ERROR", "Invalid input: " + e.getMessage());
            } catch (Exception e) {
                output.log("ERROR", "Unexpected error: " + e.getMessage());
            }
        }
    }

    private static void handleBind(String line, Keyboard keyboard, CommandRegistry registry) {
        String[] parts = line.split("\\s+");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Usage: bind <key> <command> [paramKey=paramValue...]");
        }

        String key = parts[1];
        String cmd = parts[2];

        if (!registry.hasCommand(cmd)) {
            throw new IllegalArgumentException("Unknown command type: " + cmd);
        }

        Map<String, Object> params = new HashMap<>();
        if (parts.length > 3) {
            for (int i = 3; i < parts.length; i++) {
                String[] kv = parts[i].split("=");
                if (kv.length == 2) {
                    params.put(kv[0], kv[1]);
                } else {
                    throw new IllegalArgumentException("Invalid param format: " + parts[i]);
                }
            }
        }
        keyboard.bind(key, cmd, params);
        System.out.println("Bound '" + key + "' to '" + cmd + "'");
    }
    //endregion

    //region helpers
    private static char getChar(Map<String, Object> params, String key) {
        Object v = params.get(key);
        if (v == null) throw new IllegalArgumentException("Missing param: " + key);
        String s = String.valueOf(v);
        if (s.isEmpty()) throw new IllegalArgumentException("Empty char param: " + key);
        return s.charAt(0);
    }
    //endregion
}
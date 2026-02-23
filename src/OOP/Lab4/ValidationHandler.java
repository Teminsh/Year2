package OOP.Lab4;

class NotEmptyValidator implements EventHandler<PropertyChangingEventArgs> {
    @Override
    public void handle(Object sender, PropertyChangingEventArgs args) {
        if (args.getNewValue() instanceof String s) {
            if (s.trim().isEmpty()) {
                System.out.println("[VALIDATION] Отклонено: свойство '" + args.getPropertyName() + "' не может быть пустой строкой.");
                args.setCanChange(false);
            }
        }
    }
}

class PositiveNumberValidator implements EventHandler<PropertyChangingEventArgs> {
    @Override
    public void handle(Object sender, PropertyChangingEventArgs args) {
        if (args.getNewValue() instanceof Number n) {
            if (n.doubleValue() < 0) {
                System.out.println("[VALIDATION] Отклонено: свойство '" + args.getPropertyName() + "' не может быть отрицательным (" + n + ").");
                args.setCanChange(false);
            }
        }
    }
}
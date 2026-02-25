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

class RangeValidator implements EventHandler<PropertyChangingEventArgs> {
    private final String targetProperty;
    private final double minValue;
    private final double maxValue;

    public RangeValidator (String targetProperty, double minValue, double maxValue) {
        this.targetProperty = targetProperty;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void handle(Object sender, PropertyChangingEventArgs args)
    {
        if (!targetProperty.equals(args.getPropertyName())) return;

        if (args.getNewValue() instanceof Number n) {
            double value = n.doubleValue();
            if (value < minValue || value > maxValue) {
                System.out.println("[VALIDATION] Отклонено: '" + args.getPropertyName() +
                        "' должно быть в [" + minValue + ", " + maxValue +
                        "], а не " + value);
                args.setCanChange(false);
            }
        }
    }
}
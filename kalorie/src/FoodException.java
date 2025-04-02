public class FoodException extends Exception {
    private String errorType;

    public FoodException(String message) {
        super(message);
    }

    public FoodException(String message, String errorType) {
        super(message);
        this.errorType = errorType;
    }

    public String getErrorType() {
        return errorType;
    }
}
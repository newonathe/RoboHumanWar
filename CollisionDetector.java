public class CollisionDetector {
    int x;
    int y;
    int width;
    int height;

    // if need lang natin gumawa ng rectangle na magfafollow sa 
    // isang object
    public CollisionDetector(GameObject gameObject) {
        this.x = gameObject.getX();
        this.y = gameObject.getY();
        this.width = gameObject.getWidth();
        this.height = gameObject.getHeight();
    }

    public boolean checkCollision(GameObject other) {
        return (x < other.getX() + other.getWidth() &&
                x + this.width > other.getX() &&
                y < other.getY() + other.getHeight() &&
                y + this.height > other.getY());
    }

}

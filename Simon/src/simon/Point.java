package simon;

public class Point {

    float x;
    float y;

    public Point() {
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Point add(Point p){
        Point np = new Point(this);
        np.x += p.x;
        np.y += p.y;
        return np;
    }
    
    public Point subtract(Point p){
        Point np = new Point(this);
        np.x -= p.x;
        np.y -= p.y;
        return np;
    }
    
    public Point multiply(Point p){
        Point np = new Point(this);
        np.x *= p.x;
        np.y *= p.y;
        return np;
    }
    
    public float distanceTo(Point v) {
        return (float) Math.sqrt(this.distanceToSquared(v));
    }
    
    public float distanceToSquared(Point v){
        return (float) (Math.pow((this.x - v.x), 2) + Math.pow((this.y - v.y), 2));
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Float.floatToIntBits(this.x);
        hash = 29 * hash + Float.floatToIntBits(this.y);
        return hash;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
            return false;
        }
        return true;
    }
    
    
}

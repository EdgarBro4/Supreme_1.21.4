import org.joml.*; public class TestJoml { public static void main(String[] a) { Vector4f v = new Vector4f(1,2,3,1); v.mul(new Matrix4f().translation(10,10,10)); System.out.println(v); } }

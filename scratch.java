import net.minecraftforge.client.event.AddFramePassEvent;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class scratch {
    public static void main(String[] args) throws Exception {
        System.out.println("Methods:");
        for (Method m : AddFramePassEvent.class.getDeclaredMethods()) {
            System.out.println(m.toString());
        }
        System.out.println("Fields:");
        for (Field f : AddFramePassEvent.class.getDeclaredFields()) {
            System.out.println(f.toString());
        }
        System.out.println("Bundle Methods:");
        for (Method m : net.minecraftforge.client.event.AddFramePassEvent.Bundle.class.getDeclaredMethods()) {
            System.out.println(m.toString());
        }
        System.out.println("Bundle Fields:");
        for (Field f : net.minecraftforge.client.event.AddFramePassEvent.Bundle.class.getDeclaredFields()) {
            System.out.println(f.toString());
        }
    }
}

import java.util.Arrays;
public class ByteSet {
    private byte[] elements;

    // Конструктор для создания множества без начальной инициализации
    public ByteSet(int size) {
        elements = new byte[size];
    }

    // Конструктор для создания множества с начальной инициализацией
    public ByteSet(byte[] arr) {
        elements = arr;
    }

    // Метод для проверки принадлежности значения множеству
    public boolean contains(byte value) {
        for (byte element : elements) {
            if (element == value) {
                return true;
            }
        }
        return false;
    }

    // Метод для вывода на печать элементов множества
    public void printElements() {
        System.out.println(Arrays.toString(elements));
    }

    // Метод для объединения двух множеств
    public ByteSet union(ByteSet otherSet) {
        int newSize = elements.length + otherSet.elements.length;
        byte[] unionElements = new byte[newSize];
        System.arraycopy(elements, 0, unionElements, 0, elements.length);
        System.arraycopy(otherSet.elements, 0, unionElements, elements.length, otherSet.elements.length);
        return new ByteSet(unionElements);
    }

    // Метод для присваивания одного множества другому
    public void assign(ByteSet otherSet) {
        elements = Arrays.copyOf(otherSet.elements, otherSet.elements.length);
    }

    // Пример использования класса
    public static void main(String[] args) {
        byte[] arr1 = {1, 3, 5};
        ByteSet set1 = new ByteSet(arr1);
        ByteSet set2 = new ByteSet(5);

        set2.elements[0] = 2;
        set2.elements[1] = 4;
        set2.elements[2] = 6;
        set2.elements[3] = 8;
        set2.elements[4] = 7;

        System.out.println("Множество 1:");
        set1.printElements();

        System.out.println("Множество 2:");
        set2.printElements();

        ByteSet unionSet = set1.union(set2);
        System.out.println("Объединение множеств:");
        unionSet.printElements();

        System.out.println("Множество 1 содержит 3: " + set1.contains((byte)3));
        System.out.println("Множество 2 содержит 3: " + set2.contains((byte)3));

        set2.assign(set1);
        System.out.println("Множество 2 после присваивания множества 1:");
        set2.printElements();
    }
}
import java.util.EmptyStackException;

class GenericStack<T> {

    private Object[] elements;
    private int top;
    private int capacity;

    GenericStack(int capacity) {
        this.capacity = capacity;
        elements = new Object[capacity];
        top = -1;
    }

    // Push element into stack
    public void push(T value) {

        if (top == capacity - 1) {
            System.out.println("Stack is Full!");
            return;
        }

        elements[++top] = value;

        System.out.println("Pushed: " + value);
    }

    // Pop element from stack
    @SuppressWarnings("unchecked")
    public T pop() {

        if (isEmpty()) {
            throw new EmptyStackException();
        }

        T value = (T) elements[top];

        elements[top] = null;
        top--;

        return value;
    }

    // Get top element
    @SuppressWarnings("unchecked")
    public T peek() {

        if (isEmpty()) {
            throw new EmptyStackException();
        }

        return (T) elements[top];
    }

    // Check whether stack is empty
    public boolean isEmpty() {
        return top == -1;
    }

    // Display stack
    public void display() {

        if (isEmpty()) {
            System.out.println("Stack is Empty!");
            return;
        }

        System.out.println("Stack Elements:");

        for (int i = top; i >= 0; i--) {
            System.out.println(elements[i]);
        }
    }
}

public class GenericStackDemo {

    public static void main(String[] args) {

        // Integer Stack
        GenericStack<Integer> intStack =
                new GenericStack<>(5);

        System.out.println("Integer Stack:");

        intStack.push(10);
        intStack.push(20);
        intStack.push(30);

        intStack.display();

        System.out.println("Top Element: " + intStack.peek());
        System.out.println("Popped Element: " + intStack.pop());

        intStack.display();

        // String Stack
        System.out.println("\nString Stack:");

        GenericStack<String> stringStack =
                new GenericStack<>(5);

        stringStack.push("Java");
        stringStack.push("Python");
        stringStack.push("C++");

        stringStack.display();

        System.out.println("Top Element: "
                + stringStack.peek());

        System.out.println("Popped Element: "
                + stringStack.pop());

        stringStack.display();

        // Empty stack handling
        System.out.println("\nEmpty Stack Test:");

        GenericStack<Integer> emptyStack =
                new GenericStack<>(3);

        try {
            System.out.println(emptyStack.pop());
        } catch (EmptyStackException e) {
            System.out.println("Cannot pop: Stack is Empty!");
        }
    }
}
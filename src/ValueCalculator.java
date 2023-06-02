public class ValueCalculator {
    private float[] array;
    private int size;
    private int halfSize;

    public ValueCalculator(int size) {
        if (size < 1000000) {
            this.size = 1000000;
        } else {
            this.size = size;
        }
        this.halfSize = this.size / 2;
        this.array = new float[this.size];
    }

    public int getSize() {
        return size;
    }

    public int getHalfSize() {
        return halfSize;
    }

    public void calculateValues() {
        long start = System.currentTimeMillis();

        // Заповнення масиву одиницями або будь-якими іншими однаковими значеннями
        for (int i = 0; i < size; i++) {
            array[i] = 1.0f;
        }

        float[] a1 = new float[halfSize];
        float[] a2 = new float[halfSize];

        // Розбиття масиву на два масиви однакової величини
        System.arraycopy(array, 0, a1, 0, halfSize);
        System.arraycopy(array, halfSize, a2, 0, halfSize);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < halfSize; i++) {
                int index = i;
                a1[index] = (float) (array[index] * Math.sin(0.2f + index / 5) * Math.cos(0.2f + index / 5) * Math.cos(0.4f + index / 2));
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < halfSize; i++) {
                int index = i;
                a2[index] = (float) (array[halfSize + index] * Math.sin(0.2f + (halfSize + index) / 5) * Math.cos(0.2f + (halfSize + index) / 5) * Math.cos(0.4f + (halfSize + index) / 2));
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Зворотне склеювання двох масивів в один початковий
        System.arraycopy(a1, 0, array, 0, halfSize);
        System.arraycopy(a2, 0, array, halfSize, halfSize);

        long end = System.currentTimeMillis();
        long executionTime = end - start;

        System.out.println("Час виконання: " + executionTime + " мс");
    }
}

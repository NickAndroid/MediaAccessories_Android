package dev.nick.imageloader.queue;

/**
 * Created by nick on 16-8-9.
 */
public enum Priority {

    HIGH(0, new Sequencer<Priority>() {
        @Override
        public Priority lower() {
            return NORMAL;
        }

        @Override
        public Priority higher() {
            return null;
        }

        @Override
        public boolean isHigherThan(Priority priority) {
            return true;
        }

        @Override
        public boolean isLowerThan(Priority priority) {
            return false;
        }
    }),
    NORMAL(0, new Sequencer<Priority>() {
        @Override
        public Priority lower() {
            return LOW;
        }

        @Override
        public Priority higher() {
            return HIGH;
        }

        @Override
        public boolean isHigherThan(Priority priority) {
            return priority == LOW;
        }

        @Override
        public boolean isLowerThan(Priority priority) {
            return priority == HIGH;
        }
    }),
    LOW(0, new Sequencer<Priority>() {
        @Override
        public Priority lower() {
            return null;
        }

        @Override
        public Priority higher() {
            return NORMAL;
        }

        @Override
        public boolean isHigherThan(Priority priority) {
            return false;
        }

        @Override
        public boolean isLowerThan(Priority priority) {
            return true;
        }
    });

    long timeoutMillSec;
    Sequencer<Priority> sequencer;

    Priority(long timeoutMillSec, Sequencer<Priority> sequencer) {
        this.timeoutMillSec = timeoutMillSec;
        this.sequencer = sequencer;
    }

    interface Sequencer<T> {
        T lower();

        T higher();

        boolean isHigherThan(T t);

        boolean isLowerThan(T t);
    }
}
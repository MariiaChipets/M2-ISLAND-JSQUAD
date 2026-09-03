
public class LifeGenerator {
}

// ХИЖАКИ
class Wolf extends Animal {
    public Wolf() {
        super("Вовк");
    }

    @Override
    protected Animal createNewInstance() {
        return new Wolf();
    }
}

class Snake extends Animal {
    public Snake() {
        super("Удав");
    }

    @Override
    protected Animal createNewInstance() {
        return new Snake();
    }
}

class Fox extends Animal {
    public Fox() {
        super("Лисиця");
    }

    @Override
    protected Animal createNewInstance() {
        return new Fox();
    }
}

class Bear extends Animal {
    public Bear() {
        super("Ведмідь");
    }

    @Override
    protected Animal createNewInstance() {
        return new Bear();
    }
}

class Eagle extends Animal {
    public Eagle() {
        super("Орел");
    }

    @Override
    protected Animal createNewInstance() {
        return new Eagle();
    }
}

// ТРАВОЇДНІ (ВСЕЇДНІ)
class Horse extends Animal {
    public Horse() {
        super("Кінь");
    }

    @Override
    protected Animal createNewInstance() {
        return new Horse();
    }
}

class Deer extends Animal {
    public Deer() {
        super("Олень");
    }

    @Override
    protected Animal createNewInstance() {
        return new Deer();
    }
}

class Rabbit extends Animal {
    public Rabbit() {
        super("Кролик");
    }

    @Override
    protected Animal createNewInstance() {
        return new Rabbit();
    }
}

class Goat extends Animal {
    public Goat() {
        super("Коза");
    }

    @Override
    protected Animal createNewInstance() {
        return new Goat();
    }
}

class Sheep extends Animal {
    public Sheep() {
        super("Вівця");
    }

    @Override
    protected Animal createNewInstance() {
        return new Sheep();
    }
}

class Buffalo extends Animal {
    public Buffalo() {
        super("Буйвол");
    }

    @Override
    protected Animal createNewInstance() {
        return new Buffalo();
    }
}

class Boar extends Animal {
    public Boar() {
        super("Кабан");
    }

    @Override
    protected Animal createNewInstance() {
        return new Boar();
    }
}

class Duck extends Animal {
    public Duck() {
        super("Качка");
    }

    @Override
    protected Animal createNewInstance() {
        return new Duck();
    }
}

class Mouse extends Animal {
    public Mouse() {
        super("Миша");
    }

    @Override
    protected Animal createNewInstance() {
        return new Mouse();
    }
}

class Caterpillar extends Animal {
    public Caterpillar() {
        super("Гусінь");
    }

    @Override
    protected Animal createNewInstance() {
        return new Caterpillar();
    }
}
The 2019 Code.

There are some classes that make use of `this`, so here is an explanation:

**`this.variable`**:

```Java
public class ThisExampleBad {
    double var = 0;
    public ThisExampleBad(double var) {
        var = var;
    }
}
```

In the above code example, we havea huge problem: there are two variables called `var`: one in the parameter (`ThisExampleBad(double var)`) and one in the class (`double var = 0;`).

The above code will actually set the parameter equal to itself! How do we resolve this?

To call the variable in the class (`double var = 0;`), we add a `this.`. `this` is a keyword that means "this class" or "this object".
It basically just access other parts of itself. In this case, `this.var` would access the class's `var` instead of the parameter `var`.

No matter what the situation is, `this.var` calls a global variable (the class's var), while `var` calls a local variable if it exists (the parameter's `var`).
If there is no local variable called `var`, it calls the global variable.

Here is the fixed code:

```Java
public class ThisExampleGood {
    double var = 0;
    public ThisExampleAlsoGood(double var) {
        this.var = var;
    }
}
```

NOTE: The code above will perform the same operation as the code below:

```Java
public class ThisExampleAlsoGood {
    double var = 0;
    public ThisExampleAlsoGood(double var1) {
        var = var1;
    }
}
```

**`this(param)`**

Sometimes, constructors do a lot of the same stuff as others except for one line.

For example, let's say you want two that set a speed, but one of them should set a turn and the other default to 0. You could do the following:

```Java
int speed = 0, turn = 0;

public ThisExampleWorks(int a) {
    speed = a;
}

public ThisExampleWorks(int a, int b) {
    speed = a;
    turn = b;
}
```

(NOTE: Those are constructors, not functions. Constructors create an object with the parameters affecting some of the default values; in this case, they set speed and turn.)

However, as you add more variables, the code can get *really* long, and there may be multiple constructors. For example:

```Java
double speed = 0, turn = 0;
boolean driveStraight = false;
long timeout = 0;

public ThisExampleIsLong(double speed) {
    this.speed = speed;
}

public ThisExampleIsLong(double speed, double turn) {
    this.speed = speed;
    this.turn = turn;
}

public ThisExampleIsLong(double speed, double turn, long timeout) {
    this.speed = speed;
    this.turn = turn;
    this.timeout = timeout;
}

public ThisExampleIsLong(double speed, double turn, long timeout, boolean driveStraight) {
    this.speed = speed;
    this.turn = turn;
    this.timeout = timeout;
    this.driveStraight = driveStraight;
}
...
```

Instead of this *really* long example, we can simplify things with a `this`. Remember what `this` does? It calls "this class" or "this object".
In `ThisExampleWorks(int speed, int turn)`, if you call `this(speed)`, it will actually create a new object using the `ThisExampleWorks(int speed)` consstructor
before modifying `this.turn = turn;`. This can be extremely useful in longer code such as `ThisExampleIsLong`.

There are two ways to implement this: one is the trickle-down method; the other is the complete opposite.

Basically, you can either have the longer constructor call a smaller one (Method 1), or you can have the smaller ones call the largest one (Method 2).

All the code below runs the same as the code above, but it is noticeably cleaner.

Method 1:

```Java
double speed = 0, turn = 0;
boolean driveStraight = false;
long timeout = 0;

public ThisExampleIsLong(double speed) {
    this.speed = speed;
}

public ThisExampleIsLong(double speed, double turn) {
    this(speed);
    this.turn = turn;
}

public ThisExampleIsLong(double speed, double turn, long timeout) {
    this(speed, turn);
    this.timeout = timeout;
}

public ThisExampleIsLong(double speed, double turn, long timeout, boolean driveStraight) {
    this(speed, turn, timeout);
    this.driveStraight = driveStraight;
}
...
```

Method 2:

```Java
double speed = 0, turn = 0;
boolean driveStraight = false;
long timeout = 0;

public ThisExampleIsLong(double speed) {
    this(speed, 0, 0, false);
}

public ThisExampleIsLong(double speed, double turn) {
    this(speed, turn, 0, false);
}

public ThisExampleIsLong(double speed, double turn, long timeout) {
    this(speed, turn, timeout, false);
}

public ThisExampleIsLong(double speed, double turn, long timeout, boolean driveStraight) {
    this.speed = speed;
    this.turn = turn;
    this.timeout = timeout;
    this.driveStraight = driveStraight;
}
...
```

**Method 1 is recommended in most situations.** Even though both run the same way, Method 2 is harder to read since you always need to go back to the largest constructor
to understand what the defaults are affecting (For example, what does `this(speed, 0, 0, false)` do? What do the two zeroes and the `false` change?).

Hopefully this clears up confusion on the `this` keyword in Java.
Tl;dr:
- `this.var` calls the global variable in the class instead of the local variable or the parameter. You only need to call `this.` if there are two variables with the same name.
- `this(param)` calls the constructor that accepts that parameter so you don't need to copy-paste code between constructors.
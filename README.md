# MathFromString

Parses a string as a mathematical expression. Supports addition, subtraction, division, multiplication, and exponentiation.

This project is a port of my JavaScript [math-from-string](https://github.com/Grafluxe/math-from-string) project.

## Samples

#### MathFromString.parse(str: String): Double

```
MathFromString.parse("2+2")
// 4.0

MathFromString.parse("(5-3)*2")
// 4.0

MathFromString.parse("5-3*2")
// -1.0

MathFromString.parse("-10+-2")
// -12.0

MathFromString.parse("2**3")
// 8.0

MathFromString.parse("(2+8)*2+(100.34-10+(2+5.4))")
// 117.74000000000001
```

## License

Copyright (c) 2014, 2017-2018 Leandro Silva (http://grafluxe.com)

Released under the MIT License.

See LICENSE.md for entire terms.

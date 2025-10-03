# Assignment 2. Selection Sort

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/license/mit/)

## Usage examples

Basic Selection Sort algorithm

```java
List<Integer> array = new ArrayList<>(Arrays.asList(5, 4, 3, 6, 1, 2));
List<Integer> arraySorted = SelectionSort.sort(array);
```

Optimized Selection Sort algorithm

```java
List<Integer> array = new ArrayList<>(Arrays.asList(5, 4, 3, 6, 1, 2));
List<Integer> arraySorted = SelectionSort.sortOptimized(array);
```

## Time vs N plot

![time_vs_n](/assignment2/performance-plots/time_vs_n.png)

## Requirements

* [JDK 24](https://jdk.java.net/24/)[^1]
* [Apache Maven](https://maven.apache.org/install.html)


[^1]: Tested on [JDK 24](https://jdk.java.net/24/), but should run fine on [JDK 21](https://jdk.java.net/archive/) too

import pandas
import matplotlib.pyplot


df = pandas.read_csv("../metrics.csv")

matplotlib.pyplot.plot(df["size"], df["time"], label="Selection Sort")

matplotlib.pyplot.xlabel("Input size (n)")
matplotlib.pyplot.ylabel("Time (ns)")
matplotlib.pyplot.legend()
matplotlib.pyplot.savefig("time_vs_n.png")
matplotlib.pyplot.clf()

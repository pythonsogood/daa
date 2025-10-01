import pandas
import matplotlib.pyplot


df = pandas.read_csv("../metrics.csv")

for algo in df["algorithm"].unique():
	sub = df[df["algorithm"] == algo]
	matplotlib.pyplot.plot(sub["n"], sub["time_ns"], label=algo)
	print(sub["time_ns"])

matplotlib.pyplot.xlabel("Input size (n)")
matplotlib.pyplot.ylabel("Time (ns)")
matplotlib.pyplot.legend()
matplotlib.pyplot.savefig("time_vs_n.png")
matplotlib.pyplot.clf()

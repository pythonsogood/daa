import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("../metrics.csv")

df["x"] = df["n"] + df["edges"]

df = df.sort_values(by="x")

plt.figure(figsize=(7, 5), dpi=150)

plt.plot(df["x"], df["total_time"], marker="o", linestyle="-", color="steelblue")

plt.xlabel("Input size (n + edges)")
plt.ylabel("Time (ns)")
plt.tight_layout()
plt.savefig("time_vs_ve.png")

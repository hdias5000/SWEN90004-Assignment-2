import csv
import matplotlib.pyplot as plt
import numpy as np
import subprocess
from threading import Thread

plt.rcdefaults()

def plt_histogram(num_low, num_mid, num_high):
    categories = ('low', 'mid', 'high')
    x_pos = np.arange(len(categories))
    y_pos = (num_low, num_mid, num_high)
    fig = plt.figure()
    ax1 = fig.add_subplot(111)
    # ax1.clf()
    plt.bar(x_pos, y_pos)
    plt.xticks(x_pos, categories)
    plt.title("number of people in 3 wealth levels")
    return fig

def plt_num_categories_over_time(ticks, nums_low, nums_mid, nums_high):
    fig = plt.figure()
    ax1 = fig.add_subplot(111)
    # ax1.clf()
    plt.plot(ticks, nums_low, label="low")
    plt.plot(ticks, nums_mid, label="mid")
    plt.plot(ticks, nums_high, label="high")
    plt.legend()
    plt.title("number of people in 3 wealth levels over time")
    return fig

def plt_lorenz_curve(tick, curve_str_list):
    fig = plt.figure()
    ax1 = fig.add_subplot(111)
    # ax1.clf()
    x = []
    y = []
    for s1, s2 in [s.split(';') for s in curve_str_list]:
        x.append(float(s1.strip().strip('%')))
        y.append(float(s2.strip().strip('%')))
    plt.plot(x, y, label="curve")
    plt.plot(x, x, label="y=x")
    plt.title("lorenz curve at tick " + str(tick))
    plt.legend()
    return fig

def plot_and_save_all(csv_filepath, line_filepath, hist_filepath, lorenz_filepath):
    f = open(csv_filepath)
    lines = csv.reader(f)

    # skip header
    next(lines)

    ticks = []
    nums_low = []
    nums_mid = []
    nums_high = []
    gini_indices = []
    lorenz_curve = []

    for row in lines:
        ticks.append(int(row[0]))
        nums_low.append(int(row[1]))
        nums_mid.append(int(row[2]))
        nums_high.append(int(row[3]))
        gini_indices.append(float(row[4]))
        lorenz_curve = row[5:]

    hist = plt_histogram(nums_low[-1], nums_mid[-1], nums_high[-1])
    hist.savefig(hist_filepath)
    line = plt_num_categories_over_time(ticks, nums_low, nums_mid, nums_high)
    line.savefig(line_filepath)
    lorenz = plt_lorenz_curve(ticks[-1], lorenz_curve)
    lorenz.savefig(lorenz_filepath)
    f.close()


# class ExperimentThread(Thread):
#     def __init__(self, java_args):
#         Thread.__init__(self)
#         self.__java_args = java_args
#         self.__csv_filename = java_args[9]
    
#     def run(self):
#         # run Java program, make csv
#         print(subprocess.run(['java', '-cp', 'program.jar', 'Simulation'] + self.__java_args))
#         # make plots from csv
#         plot_and_save_all(
#             self.__csv_filename,
#             self.__csv_filename.split(".")[0] + "_line.png",
#             self.__csv_filename.split(".")[0] + "_hist.png",
#             self.__csv_filename.split(".")[0] + "_lorenz.png"
#         )

def call_java_and_plot(java_args):
    try:
        # run Java program, make csv
        subprocess.run(['java', '-cp', 'program.jar', 'Simulation'] + java_args)
        csv_filename = java_args[9]
        # make plots from csv
        plot_and_save_all(
            csv_filename,
            csv_filename + ".line.png",
            csv_filename + ".hist.png",
            csv_filename + ".lorenz.png"
        )
    except Exception as e:
        print(e)

import os
import sys

args = sys.argv[1:]
# print(args)
call_java_and_plot(args)



# max_life_expectancy_values = range(10, 100, 10)


# from multiprocessing import Process
# from multiprocessing import Pool
# for max_life_exp in max_life_expectancy_values:
#     # ExperimentThread([
#     #     "250", # num people
#     #     "5", # max vision
#     #     "15", # metabolism_max
#     #     "1", # life expectancy min
#     #     str(max_life_exp), # life expectancy max
#     #     "0.1", # percent best land
#     #     "1.0", # grain growth interval
#     #     "4", # grain growth rate
#     #     "1000", # time max
#     #     "output/max_exp_{}.csv".format(max_life_exp) # csv filename
#     # ]).start()
#     Process(target=call_java_and_plot, args=([
#         "250", # num people
#         "5", # max vision
#         "15", # metabolism_max
#         "1", # life expectancy min
#         str(max_life_exp), # life expectancy max
#         "0.1", # percent best land
#         "1.0", # grain growth interval
#         "4", # grain growth rate
#         "1000", # time max
#         "output/max_exp_{}.csv".format(max_life_exp) # csv filename
#     ],)).start()

# with Pool(processes=4) as pool:
#     print(pool.map(call_java_and_plot, [
#         [
#     "250", # num people
#     "5", # max vision
#     "15", # metabolism_max
#     "1", # life expectancy min
#     str(80), # life expectancy max
#     "0.1", # percent best land
#     "1.0", # grain growth interval
#     "4", # grain growth rate
#     "1000", # time max
#     "output/max_exp_{}.csv".format(80) # csv filename
# ]
#     ]))

# try:
#     Process(target=call_java_and_plot, args=([
#         "250", # num people
#         "5", # max vision
#         "15", # metabolism_max
#         "1", # life expectancy min
#         str(80), # life expectancy max
#         "0.1", # percent best land
#         "1.0", # grain growth interval
#         "4", # grain growth rate
#         "1000", # time max
#         "output/max_exp_{}.csv".format(80) # csv filename
#     ],)).start()
# except Exception as e:
#     print(e)
    
# call_java_and_plot(
#     [
#     "250", # num people
#     "5", # max vision
#     "15", # metabolism_max
#     "1", # life expectancy min
#     str(80), # life expectancy max
#     "0.1", # percent best land
#     "1.0", # grain growth interval
#     "4", # grain growth rate
#     "1000", # time max
#     "output/max_exp_{}.csv".format(80) # csv filename
# ]
# )

# from concurrent.futures import ProcessPoolExecutor
# with ProcessPoolExecutor(max_workers=1) as executor:
#     future = executor.submit(call_java_and_plot, 
#         [
#     "250", # num people
#     "5", # max vision
#     "15", # metabolism_max
#     "1", # life expectancy min
#     str(80), # life expectancy max
#     "0.1", # percent best land
#     "1.0", # grain growth interval
#     "4", # grain growth rate
#     "1000", # time max
#     "output/max_exp_{}.csv".format(80) # csv filename
# ]
#     )
#     print(future.result())
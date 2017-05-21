max_threads = 10

import numpy as np
# max_life_expectancy_values = [80]
max_life_expectancy_values = [5]
percent_best_land_values = [0.1]
growth_interval_values = [1]
growth_amount_values = [4]
vision_values = [5]

def worker(args): 
    subprocess.call(["python3", "plot.py"] + args)

from multiprocessing.pool import ThreadPool
import subprocess

from itertools import product
commands = []
for vision, max_life_exp, percent_best_land, interval, amount in product(
    vision_values, max_life_expectancy_values, percent_best_land_values, growth_interval_values, growth_amount_values):
    commands.append( [
        "250", # num people
        str(vision), # max vision
        "15", # metabolism_max
        "1", # life expectancy min
        str(max_life_exp), # life expectancy max
        str(percent_best_land), # percent best land
        "1.0", # grain growth interval
        "4", # grain growth rate
        "1000", # time max
        "output/vision{}_maxexp{}_percentbest{:.2f}_growthinterval{}_growthamount{}.csv".format(
            vision, max_life_exp, percent_best_land, interval, amount
        ) # csv filename
    ] )

print("{} tasks in total, starting...".format(len(commands)))
x = 0
for i in ThreadPool(max_threads).imap_unordered(worker, commands):
    print("finsihed task {}".format(x))
    x += 1

print("creating table of gini indices...")

import csv
def get_final_gini(input_filename):
    f = open(input_filename)
    lines = csv.reader(f)
    next(lines)
    for row in lines:
        gini = float(row[4])
    f.close()
    return gini

def make_gini_table(input_filenames, output_file):
    f = open(output_file, 'w')
    writer = csv.writer(f)
    for file in input_filenames:
        writer.writerow([file, get_final_gini(file)])
    f.close()

import os
filenames = ['output/' + f for f in os.listdir('output/')
    if f.endswith('.csv') and not f.endswith('gini_table.csv')]
make_gini_table(filenames, "output/gini_table.csv")
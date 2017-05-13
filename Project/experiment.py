max_threads = 10

import numpy as np
max_life_expectancy_values = range(10, 120, 40)
percent_best_land_values = np.arange(0.1, 0.5, 0.1)


def worker(args): 
    subprocess.call(["python3", "plot.py"] + args)

from multiprocessing.pool import ThreadPool
import subprocess

from itertools import product
commands = []
for max_life_exp, percent_best_land in product(max_life_expectancy_values, percent_best_land_values):
    commands.append( [
        "250", # num people
        "5", # max vision
        "15", # metabolism_max
        "1", # life expectancy min
        str(max_life_exp), # life expectancy max
        str(percent_best_land), # percent best land
        "1.0", # grain growth interval
        "4", # grain growth rate
        "1000", # time max
        "output/max_exp_{}_percent_best_{:2f}.csv".format(max_life_exp, percent_best_land) # csv filename
    ] )

from tqdm import tqdm
for i in tqdm(ThreadPool(max_threads).imap_unordered(worker, commands)):
    pass
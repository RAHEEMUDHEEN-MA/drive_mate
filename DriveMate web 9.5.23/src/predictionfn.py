

# import tflearn
import numpy as np
# from keras.callbacks import EarlyStopping, ModelCheckpoint

# import speechData
# import tensorflow as tf
# from sklearn.model_selection import train_test_split
# from sklearn.metrics import confusion_matrix
# from keras.utils.vis_utils import plot_model
import librosa
import os
res=os.listdir("static/dataset")
print(len(res))

import csv
mydict = []
# import matplotlib.pyplot as plt
# from matplotlib import pyplot

learning_rate = 0.00001
training_iters =80  # steps
#
width = 20  # mfcc features
height = 80  # (max) length of utterance
classes = 12  # digits



from collections import Counter


class CKNN:

    def __init__(self):
        self.accurate_predictions = 0
        self.total_predictions = 0
        self.accuracy = 0.0
        ##########
        with open('labels1.data') as f:
            lines = f.readlines()
        lines=[s.strip('\n') for s in lines]
        print(lines)
        training_data=np.loadtxt("sample1.data",dtype=float,delimiter=" ")

        self.training_set= {'0':[],'1':[],'2':[],'3':[],'4':[],'5':[],'6':[],'7':[],'8':[],'9':[],'10':[],'11':[],}
        cnt=0

        for record in training_data:
            st=lines[cnt][0]
            cnt+=1


            self.training_set[st[-1]].append( record[:])

    def predict(self,  to_predict, k = 1):


        distributions = []
        for group in self.training_set:
            i=0
            # print(group,'group')
            for features in self.training_set[group]:

                euclidean_distance = np.linalg.norm(np.array(features)- np.array(to_predict))

                distributions.append([euclidean_distance, group])

        # print(distributions)
        results = [i[1] for i in sorted(distributions)[:k]]
        result = Counter(results).most_common(1)[0][0]
        # print("rs",results,self.training_set.keys())
        confidence = Counter(results).most_common(1)[0][1]/k

        return result, confidence


def prep1(filename):

    knn = CKNN()
    #print(filename)
    feat=[filename]
    res=knn.predict(feat)
    print(res[0])
    print(res[1],"111===========================================")

    return res[0]





def mfcc_target1(predict):
    batch_features = []
    files = [predict]
    #load audio file
    for wav in files:
        if not wav.endswith(".wav"): continue
        wave, sr = librosa.load(predict, mono=True)
        #feature extraction
        mfcc = librosa.feature.mfcc(wave, sr)
        print("liborsa Mfcc = ",np.array(mfcc).shape)
        print("features are :",mfcc)
        print("===================++++++++++++++++++++++++++++++++++++++=")
        lenn = 0
        if len(mfcc[0]) < 500:

            lenn = len(mfcc[0])

        mfcc = np.pad(mfcc, ((0, 0), (0, 500 - lenn)), mode='constant', constant_values=0)
        # samples = []
        # for i in range(0, 20):
        #     row = []
        #     for j in range(80):
        #         row.append(mfcc[i][j])
        #
        #     samples.append(row)
        batch_features.append(np.array(mfcc))

    print(np.array(batch_features).shape)
    return batch_features


def userpredict(wav):
    print(wav)
    row = []
    feature = mfcc_target1(wav)
    print('-------------------------------------------------------------------------',len(feature))
    for ii in feature[0]:
        # print(len(ii), ii, "=======================")
        avg = sum(ii) / len(ii)
        row.append(avg)
    print(len(row), "=========================")
    return prep1(row)

# print("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=")
# res=userpredict(r'C:\Users\RAHEEMUDHEEN MA\PycharmProjects\PROJECT2\src\static\dataset\loweridlerpm-activa125\Track 113.wav')
# print('result',res)

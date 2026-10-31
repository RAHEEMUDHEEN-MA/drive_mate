import os
import numpy as np
import librosa
from random import shuffle

path = r"C:\Users\RAHEEMUDHEEN MA\PycharmProjects\PROJECT2\src\static\dataset/"

# // saving/loading data set
def loadDataSet():
    samples = []
    names = []

    X, Y = mfcc_generator()
    print(X[0])
    print(Y[0])
    for i in range(0,len(Y)):
        row=[]
        for ii in X[i]:
            print(len(ii),ii,"=======================")
            avg=sum(ii)/len(ii)
            row.append(avg)
        samples.append(row)
        names.append(Y[i])
    np.savetxt("sample1.data",samples)
    np.savetxt("labels1.data",names)

def mfcc_generator():

    batch_features = []
    labels = []

    filess=["normal-ns160","tappetissue-ns160","loweridlerpm-ns160","highidlerpm-ns160", "normal-activa","knoking-activa","highidlerpm-activa","loweridlerpm-activa",  "normal-activa125","knoking-activa125","highidlerpm-activa125","loweridlerpm-activa125"]

    for i in range(0,len(filess)):
        files = os.listdir(path+filess[i])
        shuffle(files)

        for wav in files:
            if not wav.endswith(".wav"): continue
            try:
                print("========================")
                wave, sr = librosa.load(path +filess[i]+"/"+ wav, mono=True)
                mfcc = librosa.feature.mfcc(wave, sr)
                lenn=0
                if len(mfcc[0])<500:
                    lenn=len(mfcc[0])
                mfcc = np.pad(mfcc, ((0, 0), (0, 500 - lenn)), mode='constant', constant_values=0)
                batch_features.append(np.array(mfcc))
                labels.append(i)

            except Exception as e:
                print(e,"===========================",path +filess[i]+"/"+ wav)
                print(path +filess[i]+"/"+ wav)

    # print(np.array(batch_features).shape)
    return batch_features, labels

loadDataSet()
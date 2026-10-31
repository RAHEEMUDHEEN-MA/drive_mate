import os
from flask import *
from werkzeug.utils import secure_filename

from src.db import *
from src.predictionfn import userpredict

app=Flask(__name__)




# user

@app.route('/login',methods=['post'])
def login():
    username=request.form['uname']
    password=request.form['password']
    qry = "select * from login where user_name=%s and password=%s"
    val = (username, password)
    res = selectone(qry,val)
    print(res)
    if res is None:
        return  jsonify({'task':'invalid'})
    else:
        type=res['type']
        return jsonify({'task':'success','lid':res['lid'],"type":type})



@app.route('/uregister',methods=['post'])
def uregister():
    print(request.form)
    name = request.form['name']
    place = request.form['place']
    post = request.form['post']
    pin = request.form['pin']
    phone = request.form['phone']
    email = request.form['email']
    username = request.form['username']
    password = request.form['password']
    qry = "insert into login values(null,%s,%s,'user')"
    val = (username, password)
    id = iud(qry, val)
    qry1 = "INSERT INTO `userlist` VALUES(NULL,%s,%s,%s,%s,%s,%s,%s)"
    val1 = (str(id), name, place, post, pin, phone, email)
    iud(qry1, val1)
    return jsonify({'task': 'success'})



@app.route('/uprofile', methods=['post'])
def uprofile():
    lid=request.form['lid']
    print(lid)
    qry = "SELECT `userlist`.*,`login`.* FROM `userlist` JOIN `login` ON `userlist`.`l_id`=`login`.lid WHERE `userlist`.`l_id`=%s"
    res = selectone(qry, lid)
    print(res,"+===================")
    return jsonify(res)


@app.route('/edituprofile',methods=['post'])
def edituprofile():

    name = request.form['name']
    place = request.form['place']
    post = request.form['post']
    pin = request.form['pin']
    phone = request.form['phone']
    email = request.form['email']
    username = request.form['username']
    password = request.form['password']
    lid=request.form['lid']
    qry = "update login set user_name=%s,password=%s where lid=%s"
    val = (username, password,lid)
    iud(qry, val)
    qry1 = "update userlist set name=%s,phone=%s,email=%s,place=%s ,post=%s,pin=%s where l_id=%s"
    val1 = (name, phone, email, place, post, pin,lid)
    iud(qry1, val1)
    return jsonify({'task': 'success'})










@app.route('/viewmechanics',methods=['post'])
def viewmechanics():
    res = selectall("SELECT * FROM `mechanic`")
    print(res)
    return jsonify(res)




@app.route('/nearest_mech',methods=['post'])
def nearest_mech():
    # qry="SELECT * FROM `worker`"
    latitude = request.form['lati']
    longitude = request.form['longi']
    # print(latitude,"66666666666666666666666666",longitude)
    qry="SELECT `mechanic`.*,location.*  ,(3959 * ACOS ( COS ( RADIANS(%s) ) * COS( RADIANS( latitude) ) * COS( RADIANS( longitude ) - RADIANS(%s) ) + SIN ( RADIANS(%s) ) * SIN( RADIANS( latitude ) ))) AS user_distance FROM `mechanic`JOIN `location` ON `mechanic`.`l_id`=`location`.`lid`  HAVING user_distance  < 20.000 "
    val=(latitude,longitude,latitude)
    s=selectall2(qry,val)
    print("jbkdjbjhsbjdbbd",val)

    # print(s,"KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK")
    return jsonify(s)














@app.route('/selectedmechanic', methods=['post'])
def selectedmechanic():
    mid=request.form['mid']
    print(mid)
    qry = "SELECT `mechanic`.*, AVG(`mecfeedback`.rating) AS rating FROM `mechanic` LEFT JOIN `mecfeedback` ON `mechanic`.`l_id`=`mecfeedback`.m_id WHERE `mechanic`.`l_id`=%s"
    res = selectone(qry, mid)
    print(res,"+===================")
    return jsonify(res)






@app.route('/sendrequest',methods=['post'])
def sentrequest():
    print(request.form)
    mid = request.form['mid']
    ulid = request.form['lid']
    work =request.form['work']
    # longitude =request.form['lon']
    # latitude =request.form['lat']
    qry ="insert into u_request values(null,%s,%s,%s,curdate(),'pending')"
    val=(mid,ulid,work)
    iud(qry,val)
    return jsonify({'task':'success'})



@app.route('/sentmecfeed',methods=['post'])
def sentmecfeed():
    print(request.form)
    mid =request.form['mid']
    uid =request.form['lid']
    feedback =request.form['feedback']
    rating = request.form['rating']

    qry="insert into mecfeedback values(null,%s,%s,%s,%s,curdate())"
    val=(mid,uid,feedback,rating)
    iud(qry,val)
    return jsonify({'task': 'success'})



#
# @app.route('/viewpetrolpumb',methods=['post'])
# def viewpetrolpumb():
#
#     res = selectall("SELECT `l_id`,`name`,`place` FROM `fuelpump`")
#     print(res)
#     return jsonify(res)




@app.route('/nearest_fuel',methods=['post'])
def nearest_fuel():
    # qry="SELECT * FROM `worker`"
    latitude = request.form['latif']
    longitude = request.form['longif']
    # print(latitude,"66666666666666666666666666",longitude)
    print("jbkdjbjhsbjdbbd")
    qry="SELECT `fuelpump`.*,location.*  ,(3959 * ACOS ( COS ( RADIANS(%s) ) * COS( RADIANS( latitude) ) * COS( RADIANS( longitude ) - RADIANS(%s) ) + SIN ( RADIANS(%s) ) * SIN( RADIANS( latitude ) ))) AS user_distance FROM `fuelpump`JOIN `location` ON `fuelpump`.`l_id`=`location`.`lid`  HAVING user_distance  < 20.068 "
    val=(latitude,longitude,latitude)
    s=selectall2(qry,val)
    print(s,"KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK")
    return jsonify(s)




@app.route('/viewvehicle',methods=['post'])
def viewvehicle():
    lid=request.form['lid']
    res = selectall2(("SELECT * FROM `vehicles` WHERE l_id=%s"),lid)
    return jsonify(res)

@app.route('/addvehicle',methods=['post'])
def addvehicle():
    uid =request.form['lid']
    regno=request.form['regno']
    name=request.form['vname']
    model=request.form['vmodel']
    rcimg=request.files['rcimg']
    rc=secure_filename(rcimg.filename)
    rcimg.save(os.path.join('static/veh',rc))

    incimg=request.files['insur']
    inc = secure_filename(incimg.filename)
    incimg.save(os.path.join('static/veh', inc))

    polimg=request.files['pollu']
    polu = secure_filename(polimg.filename)
    polimg.save(os.path.join('static/veh', polu))

    qry="insert into vehicles values (null,%s,%s,%s,%s,%s,%s,%s)"
    val=(uid,name,regno,model,rc,inc,polu)
    iud(qry,val)
    return jsonify({'task': 'success'})


@app.route('/selectedveh',methods=['post'])
def selectedveh():
    print(request.form)
    vid=request.form['vid']
    print("vehicle======",vid)
    qry = " SELECT *FROM`vehicles`WHERE `v_id`=%s"
    res = selectone(qry,vid)
    print(res)
    return jsonify(res)


@app.route('/deleteveh',methods=['post'])
def deleteveh():
    vid=request.form['vid']
    print(vid)
    qry = "DELETE FROM`vehicles` WHERE `v_id`=%s"
    iud(qry, vid)
    return jsonify({'task': 'success'})


    return



@app.route('/fuelrequest',methods=['post'])
def fuelrequest():

    pid = request.form['pid']
    uid = request.form['lid']
    fueltype = request.form['fuel_type']
    quantity = request.form['quantity']
    # longitude = request.form['longitude']
    latitude = request.form['latitude']
    qry = "insert into fueldelivaryreq values(null,%s,%s,%s,%s,curdate(),'pending')"
    val =(pid,uid,fueltype,quantity)
    iud(qry, val)
    print(iud)
    return jsonify({'task': 'success'})



@app.route('/requeststatus')
def requeststatus():
    qry=" s)elect*from fuelrequest where uid='%s'"
    res=selectall(qry)
    return jsonify(res)


@app.route('/sentappfeed',methods=['post'])
def sentappfeed():

    print(request.form)
    uid =request.form['lid']
    feedback =request.form['feedback']
    rating = request.form['rating']
    qry="insert into feedback values(null,%s,%s,%s,curdate())"
    val=(uid,feedback,rating)
    iud(qry,val)
    return jsonify({'task':'success'})



# mechanic


# @app.route('/mlogin',methods=['post'])
# def mlogin():
#     username=request.form['uname']
#     password=request.form['password']
#     qry = "select * from login where user_name=%s and password=%s"
#     val = (username, password)
#     res = selectone(qry, val)
#     if res is None:
#         return  jsonify({'task':'invalid'})
#     else:
#         return jsonify({'task':'success','lid':res['lid']})



@app.route('/mregister',methods=['post'])
def mregister():
    print(request.form)
    name = request.form['name']
    place = request.form['place']
    post = request.form['post']
    pin = request.form['pin']
    phone = request.form['phone']
    email = request.form['email']
    username = request.form['username']
    password = request.form['password']
    # latitude=0
    # longitude=0
    # qry = "insert into login values(null,%s,%s,'mechanic')"
    # val = (username, password)
    # id = iud(qry, val)
    # qry = "insert into mechanic values(null,%s,%s,%s,%s,%s,%s,%s)"
    qry = "INSERT INTO `mecrequest` VALUES(NULL,%s,%s,%s,%s,%s,%s,%s,%s)"
    val = ( name, place, post, pin, phone, email,username,password)
    iud(qry, val)
    print(val)

    return jsonify({'task': 'success'})





@app.route('/mprofile', methods=['post'])
def mprofile():
    lid=request.form['lid']
    print(lid)
    qry = "SELECT `mechanic`.*,`login`.* FROM `mechanic` JOIN `login` ON `mechanic`.`l_id`=`login`.lid WHERE `mechanic`.`l_id`=%s"
    res = selectone(qry, lid)
    print(res,"+===================")
    return jsonify(res)



@app.route('/editmprofile',methods=['post'])
def editmprofile():

    name = request.form['name']
    place = request.form['place']
    post = request.form['post']
    pin = request.form['pin']
    phone = request.form['phone']
    email = request.form['email']
    username = request.form['username']
    password = request.form['password']
    lid=request.form['lid']
    print(lid,"=================")
    qry = "update login set user_name=%s,password=%s where lid=%s"
    val = (username, password,lid)
    iud(qry, val)
    qry1 = "update mechanic set name=%s,phone=%s,email=%s,place=%s ,post=%s,pin=%s where l_id=%s"
    val1 = (name, phone, email, place, post, pin,lid)
    iud(qry1, val1)
    return jsonify({'task': 'success'})




# apple
@app.route('/viewreq',methods=['post'])
def viewreq():
    lid=request.form['lid']
    print("mec",lid)
    qry ="SELECT `userlist`.`name`,`u_request`.* FROM `userlist`INNER JOIN `u_request` ON `userlist`.`l_id`=`u_request`.`ul_id` WHERE `u_request`.`status`='pending' AND `u_request`.`ml_id`=%s"
    res = selectall2(qry,lid)
    print("data",res)
    return jsonify(res)

@app.route('/selctedreq',methods=['post'])
def selctedreq():
    reqid=request.form['rid']
    qry="SELECT `userlist`.`name`,`phone`,`u_request`.*,`location`.* FROM `userlist` INNER JOIN `u_request`ON `userlist`.`l_id`=`u_request`.`ul_id` JOIN `location`ON `location`.`lid`=u_request.`ul_id` WHERE `r_id`=%s AND `u_request`.status='pending'"
    res = selectone(qry, reqid)
    print("data",reqid,"=======" ,res)
    return jsonify(res)

@app.route('/servaccept',methods=['post'])
def servaccept():
    reqid=request.form['rid']
    qry="update u_request set status='accepted' where r_id=%s"
    iud(qry,reqid)
    return jsonify({'task':'success'})



@app.route('/servreject',methods=['post'])
def servreject():
    reqid = request.form['rid']
    qry = "update u_request set status='rejected' where r_id=%s"
    iud(qry,reqid)
    return jsonify({'task': 'success'})


@app.route('/status',methods=['post'])
def status():
    reqid = request.form['lid']
    qry = "SELECT `userlist`.`name`,`u_request`.* FROM `userlist`INNER JOIN `u_request` ON `userlist`.`l_id`=`u_request`.`ul_id` WHERE `u_request`.`status`='accepted' AND `u_request`.`ml_id`=%s"
    res = selectall2(qry,reqid)
    print(res,"-------")
    return jsonify(res)

@app.route('/servcomplete',methods=['post'])
def servcomplete():
    reqid=request.form['rid']
    qry="update u_request set status='completed' where r_id=%s"
    iud(qry,reqid)
    return jsonify({'task':'success'})




@app.route('/viewfeedback',methods=['post'])
def viewfeedback():
    lid = request.form['lid']
    print("mec",lid)
    qry="SELECT `mechanic`.l_id,`mecfeedback`.*,`userlist`.name FROM `mechanic` INNER JOIN`mecfeedback` ON `mechanic`.`l_id`=`mecfeedback`.`m_id` JOIN `userlist` ON `userlist`.`l_id`=`mecfeedback`.`u_id` WHERE `mechanic`.`l_id`=%s"
    res=selectall2(qry,lid)
    print("data",res)
    return jsonify(res)






# fuel pumb

@app.route('/fregister',methods=['post'])
def fregister():
    name = request.form['name']
    place = request.form['place']
    phone = request.form['phone']
    email = request.form['email']
    username = request.form['username']
    password = request.form['password']
    # latitude=0
    # longitude=0

    # qry = "insert into login values(null,%s,%s,'fuelpump')"
    # val = (username, password)
    # id = iud(qry, val)
    # qry = "insert into fuelpump values(null,%s,%s,%s,%s,%s)"
    # val = (id, name, email, place,phone )
    qry = "insert into fpumbrequest values(null,%s,%s,%s,%s,%s,%s)"
    val = ( name, phone,email, place, username,password)
    print(val)
    iud(qry, val)
    return jsonify({'task': 'success'})




@app.route('/fprofile', methods=['post'])
def fprofile():
    lid=request.form['lid']
    print(lid,"puuuummbbbb")
    qry = "SELECT `fuelpump`.*,`login`.* FROM `fuelpump` JOIN `login` ON `fuelpump`.`l_id`=`login`.lid WHERE `fuelpump`.`l_id`=%s"
    res = selectone(qry, lid)
    print(res,"+===================")
    return jsonify(res)




@app.route('/editfprofile',methods=['post'])
def editfprofile():

    name = request.form['name']
    place = request.form['place']
    phone = request.form['phone']
    email = request.form['email']
    username = request.form['username']
    password = request.form['password']
    lid=request.form['lid']
    print(lid,"=================")
    qry = "update login set user_name=%s,password=%s where lid=%s"
    val = (username, password,lid)
    iud(qry, val)
    qry1 = "update fuelpump set name=%s,phone=%s,email=%s,place=%s  where l_id=%s"
    val1 = (name, phone, email, place, lid)
    iud(qry1, val1)
    return jsonify({'task': 'success'})


@app.route('/viewfreq',methods=['post'])
def viewfreq():
    flid=request.form["lid"]
    print("fuelp",flid)
    qry = "SELECT `userlist`.`name`,`fueldelivaryreq`.*FROM `userlist` INNER JOIN `fueldelivaryreq`ON`userlist`.`l_id`=`fueldelivaryreq`.`l_id` WHERE `f_id`=%s AND `status`= 'pending' "
    res = selectall2(qry,flid)
    print(res)
    return jsonify(res)


@app.route('/selctedfreq',methods=['post'])
def selctedfreq():
    reqid=request.form['rid']
    print(reqid,"hhhhhhhhhhhhhhhhhhhhh")

    qry="SELECT `userlist`.`name`,`phone`,`fueldelivaryreq`.*,`location`.* FROM `userlist` INNER JOIN `fueldelivaryreq`ON `userlist`.`l_id`=`fueldelivaryreq`.`l_id` JOIN `location`ON `location`.`lid`=fueldelivaryreq.`l_id` WHERE `fdelv_id`=%s AND `fueldelivaryreq`.status='pending'"
    res = selectone(qry, reqid)
    print(res)
    # print("rid",reqid,"=======" ,res)
    return jsonify(res)



@app.route('/fdaccept',methods=['post'])
def fdaccept():
    reqid=request.form['rid']
    qry="update fueldelivaryreq set status='accepted' where fdelv_id=%s"
    iud(qry,reqid)
    return jsonify({'task':'success'})

@app.route('/fdreject',methods=['post'])
def fdreject():
    reqid = request.form['rid']
    qry = "update fueldelivaryreq set status='rejected' where fdelv_id=%s"
    iud(qry,reqid)
    return jsonify({'task': 'success'})










@app.route('/fdstatus',methods=['post'])
def fdstatus():
    reqid = request.form['lid']
    qry = "SELECT `userlist`.`name`,`fueldelivaryreq`.* FROM `userlist`INNER JOIN `fueldelivaryreq`ON `userlist`.`l_id`=`fueldelivaryreq`.`l_id` WHERE `fueldelivaryreq`.`status`='accepted' AND `fueldelivaryreq`.`f_id`=%s"
    res = selectall2(qry,reqid)
    print(res,"-------")
    return jsonify(res)

@app.route('/fdcomplete',methods=['post'])
def fdcomplete():
    reqid=request.form['rid']
    qry="update u_request set status='completed' where r_id=%s"
    iud(qry,reqid)
    return jsonify({'task':'success'})




@app.route('/addlocation',methods=['post'])
def addlocation():
    # print(request.form,"PPPPPPPPPPPPPPPPPPPP")
    lid=request.form['lid']
    latitude = request.form['lati']
    longitude = request.form['longi']
    print(lid)
    # print(latitude, "   ",longitude)
    qry="select * from location where lid=%s"
    res=selectone(qry,lid)
    # print(res,"kkkkkkkkkkkkk")
    if res is None:
        qry2 = "insert into location values (null,%s,%s,%s)"
        val1 = (lid, latitude, longitude)
        iud(qry2, val1)
        return jsonify({'task': 'valid'})
    else:
        qry1 ="update location set latitude=%s and longitude=%s where lid=%s"
        val = (lid, latitude, longitude)
        iud(qry1, val)
        return jsonify({'task': 'valid'})


@app.route('/upload_audio',methods=['post'])
def upload_audio():
    print(request.form)
    au=request.files['file']
    fn=secure_filename(au.filename)
    au.save(r"C:\Users\RAHEEMUDHEEN MA\PycharmProjects\PROJECT2\src\static\a.mp3")
    path = "static/a.wav"
    # au.save(os.path.join('static/audio', fn))
    os.system(
        'ffmpeg -i static\\a.mp3 static\\a.wav')
    rr=userpredict(path)
    os.remove(path)
    print(rr,"preddddicccnnn")
    print(rr)

    result=""

    if rr=='0':
        result="No complaint detected"
    elif rr=='1':
        result="higher idle rpm"
    elif rr=='2':
        result="lower idle rpm"

    elif rr=='3':
        result="higher idle rpm"
    elif rr=='4':
        result="No complaint detected"

    elif rr=='5':
        result="engine knocking "
    elif rr=='6':
        result="higher idle rpm"
    elif rr=='7':
        result="lower idle rpm"

    elif rr=='8':
        result="No complaint detected"
    elif rr=='9':
        result="No complaint detected"
    elif rr=='10':
        result="No complaint detected"

    else:
        result = "low engine oil level or tappet issue"

        print(result)

    return jsonify(task="success",res=result)






app.run(port=5000,host='0.0.0.0')


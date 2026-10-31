from flask import *

from src.db import *

app=Flask(__name__)
app.secret_key="1233"


@app.route('/',methods=['post','get'])
def home():
    return  render_template("HOME.html")



@app.route('/login')
def login():
    return  render_template("loginlndex.html")


@app.route('/adminhome',methods=['get','post'])
def adminhome():
        return render_template("adminindex.html")




@app.route('/logincode',methods=['get','post'])
def logincode():
    uname=request.form['textfield']
    pw=request.form['textfield2']
    qry="select * from login where user_name=%s and password=%s"
    val=(uname,pw)
    res=selectone(qry,val)
    if res is None:
        return'''<script>alert("invalid"); window.location='/login'</script>'''
    elif res['type']=='admin':
         return '''<script> window.location='/adminhome'</script>'''
    else:
        return '''<script>alert("invalid"); window.location='/login'</script>'''





@app.route('/manangemec',methods=['post','get'])
def managemec():
    qry=" select * from mechanic"
    res=selectall(qry)
    return render_template("MANGE MEC.html",val=res)

@app.route('/mecrequest',methods=['post','get'])
def mecrequest():
    qry="select * from mecrequest"
    res = selectall(qry)
    return render_template("mecrequest.html",val=res)



@app.route('/reject',methods=['post','get'])
def reject():
    id2=request.args.get('rid')
    qry="delete from mecrequest where r_id=%s"
    iud(qry, id2)
    return '''<script>alert("request from mechanic rejected successfully"); window.location='/mecrequest#cta'</script>'''



@app.route('/accept',methods=['post','get'])
def accept():
    id3 = request.args.get('rid')
    session['rid'] = id3
    qry = "select * from mecrequest where r_id=%s"
    res = selectone(qry, id3)
    name=res['name']
    place=res['place']
    post = res['post']
    pin = res['pin']
    phone = res['phone']
    email = res['email']
    username = res['username']
    password = res['password']

    qry = "insert into login values(null,%s,%s,'mechanic')"
    val = (username, password)
    id3 = iud(qry, val)

    qry = "insert into mechanic values(null,%s,%s,%s,%s,%s,%s,%s)"
    val = (id3, name, place, post, pin, phone, email)
    iud(qry, val)

    id2 = request.args.get('rid')
    qry = "delete from mecrequest where r_id=%s"
    iud(qry, id2)


    return '''<script>alert("succesfully added a mechanic"); window.location='/mecrequest#cta'</script>'''








@app.route('/addmec',methods=['post','get'])
def addmec():
    return render_template("ADD MEC.html")

@app.route('/reg',methods=['post','get'])
def reg():
    name=request.form['textfield']
    place= request.form['textfield2']
    post=request.form['textfield3']
    pin=request.form['textfield4']
    phone=request.form['textfield5']
    email=request.form['textfield6']
    username=request.form['textfield7']
    password=request.form['textfield8']
    qry="insert into login values(null,%s,%s,'mechanic')"
    val=(username,password)
    id=iud(qry,val)
    qry="insert into mechanic values(null,%s,%s,%s,%s,%s,%s,%s)"
    val=(id,name,place,post,pin,phone,email,)
    iud(qry,val)
    return '''<script>alert("succesfully added"); window.location='/manangemec'</script>'''




@app.route('/delete',methods=['post','get'])
def delete():
    id=request.args.get('id')
    qry="delete from login where lid=%s"
    iud(qry,id)
    qry="delete from mechanic  where l_id=%s"
    iud(qry,id)
    return '''<script> window.location='/manangemec#cta'</script>'''



@app.route('/managedataset',methods=['post','get'])
def managedataset():
    return render_template("managedataset.html")




@app.route('/ufeedback',methods=['post','get'])
def ufeedback():
    qry="SELECT`feedback`.*,`userlist`.`name` FROM `feedback` JOIN `userlist` ON `feedback`.`l_id`=`userlist`.`l_id`"
    res = selectall(qry)
    return render_template("U FEEDBACK.html",val=res)


@app.route('/mfeedback',methods=['post','get'])
def mfeedback():
    id=request.args.get('id')
    qry="SELECT `mecfeedback`.*,`mechanic`.`name`,`userlist`.`name`AS uname FROM `mecfeedback` JOIN `mechanic` ON `mecfeedback`.`m_id`=`mechanic`.`l_id` JOIN `userlist` ON `mecfeedback`.`u_id`=`userlist`.`l_id` where mecfeedback.m_id=%s"
    # qry="SELECT *FROM mecfeedback WHERE m_id=2"

    res = selectall2(qry,id)
    print(res)
    return render_template("viewmecfeedback.html",val=res)



@app.route('/editmec',methods=['post','get'])
def editmec():
    id=request.args.get('id')
    session['mid']=id
    qry="select * from mechanic where l_id=%s"
    res=selectone(qry,id)
    return render_template("editmec.html",val=res)

@app.route('/editmec2',methods=['post','get'])
def editmec2():

    name = request.form['textfield']
    place = request.form['textfield2']
    post = request.form['textfield3']
    pin = request.form['textfield4']
    phone = request.form['textfield5']
    email = request.form['textfield6']
    qry="update mechanic set name=%s,place=%s,post=%s,pin=%s,phone=%s,email=%s where l_id=%s"
    val=(name,place,post,pin,phone,email,session['mid'])
    iud(qry, val)

    qry = "update login set user_name=%s where lid=%s"
    val = (name, session['mid'])
    iud(qry, val)

    return '''<script>alert("succesfully updated"); window.location='/manangemec#cta'</script>'''

@app.route('/addfuelpump',methods=['post','get'])
def addfuelpump():
    qry = " select * from fuelpump"
    res = selectall(qry)
    return render_template("managefuelpumb.html",val=res)


@app.route('/addfuel1',methods=['get','post'])
def addfuel1():
    return render_template("addfuelpump.html")

@app.route('/addfuelpump2',methods=['post','get'])
def addfuelpump2():
    name = request.form['textfield']
    phone = request.form['textfield2']
    email = request.form['textfield3']
    place = request.form['textfield8']
    latitude = request.form['textfield4']
    longitude = request.form['textfield5']
    username = request.form['textfield6']
    password = request.form['textfield7']
    qry = "insert into login values(null,%s,%s,'fuelpump')"
    val = (username, password)
    id = iud(qry, val)

    qry = "insert into fuelpump values(null,%s,%s,%s,%s,%s,%s,%s)"
    # qry = "INSERT INTO fuelpump VALUES(NULL,1,2,3,4,5,6,7)"
    val = (id, name, phone,email,place,latitude, longitude)
    iud(qry, val)
    return '''<script>alert("succesfully added"); window.location='/addfuelpump#cta'</script>'''



@app.route('/deletef',methods=['post','get'])
def deletef():
    id=request.args.get('id')
    qry="delete from login where lid=%s"
    iud(qry,id)
    qry="delete from fuelpump  where l_id=%s"
    iud(qry,id)
    return '''<script> window.location='/addfuelpump'</script>'''







@app.route('/editfuel',methods=['post','get'])
def editfuel():
    id=request.args.get('id')
    session['fid'] = id
    qry="select * from fuelpump where l_id=%s"
    res = selectone(qry, id)
    return render_template("editfuel.html", val=res)


@app.route('/editfuel2',methods=['post','get'])
def editfuel2():
    name = request.form['textfield']
    phone = request.form['textfield2']
    email = request.form['textfield3']
    place = request.form['textfield6']
    latitude = request.form['textfield4']
    longitude = request.form['textfield5']
    qry="update fuelpump set name=%s,phone=%s,email=%s,place=%s,latitude=%s,longitude=%s where l_id=%s"
    val=(name,phone,email,place,latitude,longitude,session['fid'])
    iud(qry, val)

    qry = "update login set user_name=%s where lid=%s"
    val = (name, session['fid'])
    iud(qry, val)

    return '''<script>alert("succesfully updated"); window.location='/addfuelpump'</script>'''

@app.route('/fuelpumbrequest',methods=['post','get'])
def fuelpumbrequest():
    qry="select * from fpumbrequest"

    res=selectall(qry)
    print(res)
    return render_template("fuelpumbrequest.html",val=res)



@app.route('/rejectfp',methods=['post','get'])
def rejectfp():
    id4=request.args.get('frid')

    qry="delete from fpumbrequest where fr_id=%s"
    iud(qry,id4)
    return '''<script>alert("request from fuel-pumb rejected successfully"); window.location='/fuelpumbrequest#cta'</script>'''



@app.route('/acceptfp',methods=['post','get'])
def acceptfp():
    id5 = request.args.get('frid')
    session['frid'] = id5
    qry = "select * from fpumbrequest where fr_id=%s"
    res = selectone(qry, id5)
    name=res['name']
    phone = res['phone']
    email = res['email']
    place=res['place']
    lati= res['latitude']
    longi = res['longitude']
    username = res['username']
    password = res['password']

    qry = "insert into login values(null,%s,%s,'fuelpump')"
    val = (username, password)
    id5 = iud(qry, val)

    qry = "insert into fuelpump values(null,%s,%s,%s,%s,%s,%s,%s)"
    val = (id5, name,phone,email,place, lati, longi )
    iud(qry, val)

    id4 = request.args.get('frid')
    qry = "delete from fpumbrequest where fr_id=%s"
    iud(qry, id4)


    return '''<script>alert("succesfully added a fuelstation"); window.location='/mecrequest#cta'</script>'''






app.run(debug=True)
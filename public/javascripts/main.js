
// TEST modify DIV content
function newdiv(divid) {
    var node = document.createElement("DIV");
    var text = document.createTextNode("in div");
    node.appendChild(text);
    document.getElementById(divid).appendChild(node);
}


// TEST REST API
function newdivrest(divid, msg) {
    var msgnode = document.getElementById(msg);
    var divs = document.getElementById(divid);
    var xhr = new XMLHttpRequest();
    msgnode.value == "" ? restpath = "/div" : restpath = "/div?msg=" + msgnode.value;
    xhr.open("GET",restpath,true);
    xhr.send();
    xhr.onload = function() { divs.innerHTML += this.responseText };
}


// TEST insert, update db
function execsql(divid) {
	var div = document.getElementById(divid);
	var xhr = new XMLHttpRequest();
	xhr.open("GET","/db/anorm",true);
	xhr.send();
	xhr.onload = function() { div.innerHTML = "<pre>" + this.responseText + "</pre>" };
}


// DIV: clear DIV content
function emptydiv(divid) {
    var divs = document.getElementById(divid);
    while (divs.firstChild) {
        divs.removeChild(divs.firstChild);
    }
}


// DB: reset db
function resetsql(divid) {
    var div = document.getElementById(divid);
    var xhr = new XMLHttpRequest();
    xhr.open("GET","/db/reset",true);
    xhr.send();
    xhr.onload = function() { div.innerHTML = "<pre>" + this.responseText + "</pre>" };
}


// DB: search a specific key
function searchdb(divid) {
    var div = document.getElementById(divid);

    // get key value from form data
    var key = document.getElementById("key").value;

    // GET requested key
    var xhr = new XMLHttpRequest();
    xhr.open("GET","/db/" + key,true);
    xhr.send();
    xhr.onload = function() {
        reply = JSON.parse(this.responseText);
        div.innerHTML = "<pre>" + this.responseText + "</pre>";
        if(reply.length > 0) {
            document.getElementById("val").value = reply[0].value;
            document.getElementById("desc").value = reply[0].desc;
        }
    };
}


// DB: display all db content
function alldb(divid) {
    var div = document.getElementById(divid);
    var xhr = new XMLHttpRequest();
    xhr.open("GET","/db",true);
    xhr.send();
    xhr.onload = function() { div.innerHTML = "<pre>" + this.responseText + "</pre>"; };
}


// DB: insert/update record
function insertdb(divid) {
    var div = document.getElementById(divid);

    // get input and put it into JSON
    var inputkey = document.getElementById("key").value;
    var inputval = document.getElementById("val").value;
    var inputdesc = document.getElementById("desc").value;
    var data = { "key": inputkey, "value": inputval, "desc": inputdesc };

    // POST data
    var xhr = new XMLHttpRequest();
    xhr.open("POST","/db",true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(data));
    xhr.onload = function() { div.innerHTML = "<pre>" + this.responseText + "</pre>" };
}

function newdiv(divid) {
    var node = document.createElement("DIV");
    var text = document.createTextNode("in div");
    node.appendChild(text);
    document.getElementById(divid).appendChild(node);
}


function emptydiv(divid) {
    var divs = document.getElementById(divid);
    while (divs.firstChild) {
        divs.removeChild(divs.firstChild);
    }
}


function newdivrest(divid, msg) {
    var msgnode = document.getElementById(msg);
    var divs = document.getElementById(divid);
    var xhr = new XMLHttpRequest();
    msgnode.value == "" ? restpath = "/div" : restpath = "/div?msg=" + msgnode.value;
    xhr.open("GET",restpath,true);
    xhr.send();
    xhr.onload = function() { divs.innerHTML += this.responseText };
}


function execsql(divid) {
	var div = document.getElementById(divid);
	var xhr = new XMLHttpRequest();
	xhr.open("GET","/db/anorm",true);
	xhr.send();
	xhr.onload = function() { div.innerHTML = "<pre>" + this.responseText + "</pre>" };
}


function resetsql(divid) {
    var div = document.getElementById(divid);
    var xhr = new XMLHttpRequest();
    xhr.open("GET","/db/reset",true);
    xhr.send();
    xhr.onload = function() { div.innerHTML = "<pre>" + this.responseText + "</pre>" };
}
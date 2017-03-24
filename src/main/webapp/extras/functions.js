var counter = 1;
var limit = 100;
var div1 = '<div class="w3-card-8" style="padding:2em">';
var div2 = '</div>';
var large = '<div id="dynamicInput"><input class="w3-input w3-animate-input" type="text" required style="width:50%" name="myInputs[]"><label class="w3-label w3-validate w3-border">Name</label><p><input class="w3-input w3-animate-input" type="text" required style="width:50%" name="myInputs[]"><label class="w3-label w3-validate w3-border">Social Security Number</label><p><input class="w3-input w3-animate-input" type="email" required style="width:50%" name="myInputs[]"><label class="w3-label w3-validate w3-border">Email</label><p><input class="w3-input w3-animate-input" type="number" required style="width:15%" name="myInputs[]"><label class="w3-label w3-validate w3-border">Handicap</label></div>';

function addInput(divName){
     if (counter == limit)  {
          alert("You have reached the limit of adding " + counter + " participants");
     }
     else {
          var newdiv = document.createElement('div');
          newdiv.innerHTML = div1 + "<h5>Participant: " + (counter + 1) + "</h5>" + large + div2;
          document.getElementById(divName).appendChild(newdiv);
          counter++;
     }
};

function dismiss(){
	      document.getElementById('dismiss').style.display='none';
};

function dismiss2(){
    document.getElementById('dismiss2').style.display='none';
};

function myfunction() {
	var link = document.getElementById('hidden');
	if (document.getElementById('brackets').checked) {
		link.style.display = 'inline'; 
	}
	else {
		link.style.display = 'none'; 	
	}

}
var myVar=setInterval(function(){myTimer()},1000);
var asd = nextRaceDate
function myTimer() {
	if(nextRaceLocation != "notNextRace"){
		if(asd < 0){
			location.reload(true);
		}
		
		var seconds = asd;
		var numdays = Math.floor(seconds / 86400);
		var numhours = Math.floor((seconds % 86400) / 3600);
		var numminutes = Math.floor(((seconds % 86400) % 3600) / 60);
		var numseconds = ((seconds % 86400) % 3600) % 60;
		
		document.getElementById("nextRaceTime").innerHTML =  numdays + " d " + numhours + " h " + numminutes + " m " + numseconds + " s";
	
		asd = asd - 1;
	}
	else{
		document.getElementById("nextRaceTime").innerHTML =  "Nincs kovetkezo futam";
	}

	
}

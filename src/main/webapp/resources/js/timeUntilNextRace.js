var myVar=setInterval(function(){myTimer()},1000);
var asd = $nextRaceDate
function myTimer() {
	var totalSec = asd;
	var hours = parseInt( totalSec / 3600 ) % 24;
	var minutes = parseInt( totalSec / 60 ) % 60;
	var seconds = totalSec % 60;

	document.getElementById("nextRaceTime").innerHTML = (hours < 10 ? "0" + hours : hours) + " h " + (minutes < 10 ? "0" + minutes : minutes) + " s " + (seconds  < 10 ? "0" + seconds : seconds) + " m";
	asd = asd - 1;
}
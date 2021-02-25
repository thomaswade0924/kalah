$(document).ready(function() {
	$.ajax({
        type: 'GET',
        url: 'http://localhost:8080/kalah/init',
        dataType: "json"
    }).then(function(game) {
        console.log(game);
        $('#status').text(game.status);
        var houses = game.houses;
          $('#0').text(houses[0]);
          $('#1').text(houses[1]);
          $('#2').text(houses[2]);
          $('#3').text(houses[3]);
          $('#4').text(houses[4]);
          $('#5').text(houses[5]);
          $('#6').text(houses[6]);
          $('#7').text(houses[7]);
          $('#8').text(houses[8]);
          $('#9').text(houses[9]);
          $('#10').text(houses[10]);
          $('#11').text(houses[11]);
          $('#12').text(houses[12]);
          $('#13').text(houses[13]);
    });
	
	$(".seed").click(function(){

	      var uiHouses = $(".seed");
	      var houses = [];
	      var seed = this.id;
	
	      houses[0]=  $('#0')[0].innerHTML;
	      houses[13]=  $('#13')[0].innerHTML;
	
	      var index;
	      for (index = 0 ; index < uiHouses.length; ++index) {
	         houses[uiHouses[index].id]=uiHouses[index].innerHTML
	      }
	
	      var gamejson = {
	             "houses": houses ,
	             "status": $('#status')[0].innerHTML,
	             "playerA": {"name": "A"},
	             "playerB": {"name": "B"}
	             };

	      var move = {
	        "index" :  seed,
	        "game" : gamejson
	
	      };
	
	      $.ajax({
	          type: 'POST',
	          url: 'http://localhost:8080/kalah/move',
	          dataType: "json",
	          contentType: "application/json",
	          data : JSON.stringify(move)
	      }).then(function(game) {
	          console.log(game);
	          $('#status').text(game.status);
	          var houses = game.houses;
	            $('#0').text(houses[0]);
	            $('#1').text(houses[1]);
	            $('#2').text(houses[2]);
	            $('#3').text(houses[3]);
	            $('#4').text(houses[4]);
	            $('#5').text(houses[5]);
	            $('#6').text(houses[6]);
	            $('#7').text(houses[7]);
	            $('#8').text(houses[8]);
	            $('#9').text(houses[9]);
	            $('#10').text(houses[10]);
	            $('#11').text(houses[11]);
	            $('#12').text(houses[12]);
	            $('#13').text(houses[13]);
	      });
	    });
});
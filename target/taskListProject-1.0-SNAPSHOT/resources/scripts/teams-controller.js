
$(function () {
/***
 * makes json call to server to get Teams list
 */
function retrieveTeamsFromServer() {

    const teamSelector = $('#teamSelector');

    $.ajax({
        'url': "TeamServlet",
        'type': "get",
        'success': onSuccess,
        'fail': onFail
    });

    function onSuccess(data) {

        let elements='';

        data.forEach(function (team) {

            elements+="<option value="+team.name+">"+team.name+"</option>";

        });

        console.log(elements);
        teamSelector.html(elements);

    }
    function onFail() {
        console.log("Error: Cannot access Server");
    }
}

retrieveTeamsFromServer();

})
(function () {

    document.addEventListener('DOMContentLoaded',function () {
        let  i = setIntervalAndExecute(set,10000);
    }) ;


    function setIntervalAndExecute(setTimer,t) {
        setTimer();
        return(setInterval(setTimer, t));
    }

    function set () {
        fetch("/getusers", {
            headers: {
                'Content-Type': 'application/json'
            }})
            .then(res => res.json())
            .then(data => {
                let res = "";
                for (m of data) {
                    res += " <div class=\"chat_list\">\n" +
                        "       <div class=\"chat_people\">\n" +
                        "           <div class=\"chat_img\"> <img src=\"/Images/user-profile.png\" alt=\"N/A\"> </div>\n" +
                        "           <div class=\"chat_ib\">\n" +
                        "               <h5>" + m.userName + "</h5>\n" +
                        "           </div>\n" +
                        "       </div>\n" +
                        "      </div>"
                };
                document.getElementById("users").innerHTML = res ;
            }).catch(e => {
            document.getElementById("users").innerHTML = "Some error occured!";
        });
        fetch("/getmessages", {
            headers: {
                'Content-Type': 'application/json'
            }})
            .then(res => res.json())
            .then(data => {
                res = "";
                let name = document.getElementById("hiddenName").value;
                for (m of data) {
                    if(name === m.userName) {       //returning incoming message or outgoing according to the user name
                        res += "   <div class=\"incoming_msg\">\n" +
                            "            <div class=\"received_msg\">\n" +
                            "              <div class=\"received_withd_msg\">\n" +
                            "                <p><span style=\"font-weight:bold\">You: </span>" +  m.message + "</p>\n" +
                            "            </div>\n" +
                            "          </div>" +
                            "        </div>"

                    }
                    else {
                        res += "      <div class=\"outgoing_msg\">\n" +
                            "             <div class=\"sent_msg\">\n" +
                            "                 <p><span style=\"font-weight:bold\">" + m.userName + ": " +"</span>"  + m.message + "</p>\n" +
                            "              </div>" +
                            "         </div>"
                    }
                };
                document.getElementById("messages").innerHTML = res ;
            }).catch(e => {
            document.getElementById("messages").innerHTML = "Some error occured!";
            location.replace("/");          //directing to landing page if there is no data to display

        });
    }
})();
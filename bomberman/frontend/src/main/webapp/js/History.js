History = Class.extend({

    createTable: function (tableData) {
        var table = document.createElement('table');
        var tableBody = document.createElement('tbody');

        tableData.forEach(function(rowData) {
            var row = document.createElement('tr');

            rowData.forEach(function(cellData) {
                var cell = document.createElement('td');
                cell.appendChild(document.createTextNode(cellData));
                row.appendChild(cell);
            });

            tableBody.appendChild(row);
        });

        table.appendChild(tableBody);
        document.body.appendChild(table);
    },

    getHistory: function(login) {
        var jsonHistory = "";
        $.ajax({
            contentType: 'application/x-www-form-urlencoded',
            data: {
                "login": login
            },
            dataType: 'text',
            type: 'POST',
            url: gGameEngine.serverProxy.matchMakerUrl + "history",
            success: function (data) {
                var historyMapArray = JSON.parse(data);
                var container = document.getElementById("historyContainer");


                while (container.firstChild)
                    container.removeChild(container.firstChild);
                historyMapArray.map( function (results) {
                    var table = document.createElement('table');
                    table.style.border = "2px solid black";
                    table.style.borderRadius = "10px";
                    table.style.width = "100%";
                    table.style.borderCollapse = "collapse";
                    table.style.borderSpacing = "0";

                    var tableHead = document.createElement('thead');
                    tableHead.style.border = "1px solid black";
                    var headCell = document.createElement('th');
                    headCell.appendChild(document.createTextNode("Player"));
                    headCell.style.backgroundColor = "#ded6c1";
                    headCell.style.textAlign = "center";
                    headCell.style.padding = "12px";
                    headCell.style.width = "50%";
                    tableHead.appendChild(headCell);
                    headCell = document.createElement('th');
                    headCell.appendChild(document.createTextNode("Result"));
                    headCell.style.backgroundColor = "#ded6c1";
                    headCell.style.textAlign = "center";
                    headCell.style.padding = "12px";
                    tableHead.appendChild(headCell);
                    table.appendChild(tableHead);

                    var tableBody = document.createElement('tbody');

                    for (var player in results) {
                        var row = document.createElement('tr');
                        if (results.hasOwnProperty(player)) {
                            if (player === login) {
                                row.style.color = "red";
                                row.style.fontWeight = "bold";
                            }
                            var cell = document.createElement('td');
                            cell.style.textAlign = "center";
                            cell.style.padding = "6px";
                            cell.style.border = "2px solid grey";
                            cell.appendChild(document.createTextNode(player));
                            row.appendChild(cell);
                            cell = document.createElement('td');
                            cell.style.textAlign = "center";
                            cell.style.padding = "6px";
                            cell.style.border = "2px solid grey";
                            cell.appendChild(document.createTextNode(results[player]));
                            if (results[player] === 2) {
                                row.style.color = "green";
                                row.style.fontWeight = "bold";
                            }
                            row.appendChild(cell);
                            console.log(player + " " + results[player]);
                        }
                        tableBody.appendChild(row);
                    }
                    table.style.marginBottom = "30px";
                    table.appendChild(tableBody);
                    container.appendChild(table);
                    // container.appendChild(document.createElement('hr'));
                    // gameCounter++;
                });
            }
        });
        document.getElementById('historyModal').style.display='block'
    }

});
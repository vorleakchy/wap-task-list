tasksController = function () {

    function errorLogger(errorCode, errorMessage) {
        console.log(errorCode + ':' + errorMessage);
    }

    var taskPage;
    var initialised = false;

    const Team = function (id, name) {

        this.id = id;
        this.name = name;

    }

    const User = function (id, name, team) {
        this.id = id;
        this.name = name;
        this.team = team;
    }

    /**
     * makes json call to server to get task list.
     * currently just testing this and writing return value out to console
     * 111917kl
     */
    function retrieveTasksServer() {
        $.ajax("TaskServlet", {
            "type": "get",
            dataType: "json"
            // "data": {
            //     "first": first,
            //     "last": last
            // }
        }).done(displayTasksServer.bind()); //need reference to the tasksController object
    }

    /**
     * 111917kl
     * callback for retrieveTasksServer
     * @param data
     */
    function displayTasksServer(data) { //this needs to be bound to the tasksController -- used bind in retrieveTasksServer 111917kl
        console.log(data);
        tasksController.loadServerTasks(data);
    }

    function taskCountChanged() {
        var count = $(taskPage).find('#tblTasks tbody tr').length;
        $('footer').find('#taskCount').text(count);
    }

    function clearTask() {
        $(taskPage).find('form').fromObject({});
    }

    function renderTable() {
        $.each($(taskPage).find('#tblTasks tbody tr'), function (idx, row) {
            var due = Date.parse($(row).find('[datetime]').text());
            if (due.compareTo(Date.today()) < 0) {
                $(row).addClass("overdue");
            } else if (due.compareTo((2).days().fromNow()) <= 0) {
                $(row).addClass("warning");
            }
        });
    }

    function loadTasksToTable(tasks) {
        $(taskPage).find('#tblTasks tbody').empty();

        $.each(tasks, function (index, task) {
            if (!task.complete) {
                task.complete = false;
            }
            $('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
        });

        taskCountChanged();
        renderTable();
    }

    function getTeams(successCallBack, errorLog) {

        $.ajax({
            "url": "TeamServlet",
            "type": "get",
            dataType: "json",
            //"data": {task:JSON.stringify(data)},
            "success": successCallBack,
            "fail": errorLog
        });
    }


    /**
     * function to retrieve data from server database
     *
     * @param data
     */
    function getFromServer(servlet, data, successCallBack, errorLog) {

        $.ajax({
            "url": servlet,
            "type": "get",
            dataType: "json",
            "data": {task: JSON.stringify(data)},
            "success": successCallBack,
            "fail": errorLog
        });

    }

    /**
     * function to save data to the database
     * passes data to a JSON object and uses ajax to create a post
     * request to the server.
     * @param data
     */
    function storetoServer(servlet, data) {

        $.ajax({
            "url": servlet,
            "type": "post",
            dataType: "json",
            "data": {params: JSON.stringify(data)},
            "success": onSuccess,
            "fail": onFail
        })

        function onSuccess(data) {
            serverTaskData = data;

            console.log(serverTaskData);
        }

        function onFail() {
            console.log("Failed to save to Database");
        }

    }

    return {
        init: function (page, callback) {

            if (initialised) {
                callback()
            } else {
                taskPage = page;

                storageEngine.init(function () {
                    storageEngine.initObjectStore('task', function () {
                        callback();
                    }, errorLogger)
                }, errorLogger);
                $(taskPage).find('[required="required"]').prev('label').append('<span>*</span>').children('span').addClass('required');
                $(taskPage).find('tbody tr:even').addClass('even');

                $(taskPage).find('#btnAddTask').click(function (evt) {
                    evt.preventDefault();
                    $(taskPage).find('#taskCreation').removeClass('not');
                    $(taskPage).find('#userCreation').addClass('not');

                    loadUsers();
                });

                let selectedTeam = undefined;

                function loadUsers(elem) {

                    return getFromServer("UserServlet", null, retrieveUsers.bind());

                    function retrieveUsers(data) {
                        let users = $(elem).html();
                        data.forEach(user => {
                            users += "<option value='" + JSON.stringify(user) + "'>" + user.name + "</option>";
                        });

                        console.log(users);
                        $(taskPage).find(elem).html(users);
                    }
                };

                function loadUsersWithValue(elem) {

                    return getFromServer("UserServlet", null, retrieveUsers.bind());

                    function retrieveUsers(data) {
                        let users = $(elem).html();;
                        data.forEach(user => {
                            users += "<option value='" + user.id + "'>" + user.name + "</option>";
                        });

                        console.log(users);
                        $(taskPage).find(elem).html(users);
                    }
                };

                function loadTeams(elem) {

                    return getFromServer("TeamServlet", null, retrieveTeams.bind());

                    function retrieveTeams(data) {
                        let teams = $(elem).html();
                        data.forEach(team => {
                            teams += "<option value='" + team.id + "'>" + team.name + "</option>";
                        });

                        $(taskPage).find(elem).html(teams);
                    }

                };

                loadTeams('#team-filter');
                loadUsers('#userSelect');
                loadUsersWithValue('#user-filter');

                $(taskPage).find('#btnAddUser').click(function (evt) {
                    evt.preventDefault();
                    $(taskPage).find('#userCreation').removeClass('not');
                    $(taskPage).find('#taskCreation').addClass('not');

                    let teams = "";

                    getTeams(callback.bind());

                    function callback(data) {

                        selectedTeam = new Team(data[0].id, data[0].name);

                        data.forEach(team => {

                            teams += "<option value='" + team.id + "'>" + team.name + "</option>"

                        })

                        $(taskPage).find('#userTeam').html(teams);

                    }

                });


                $(taskPage).find('#userTeam').on('change', function (evt) {

                    evt.preventDefault();
                    let selectedUserID = $(evt.target).val();

                    selectedTeam = new Team(selectedUserID, null);

                });

                $(taskPage).find('#btnSaveUser').on('click', function () {

                    let userName = $(taskPage).find('#userName').val();

                    let user = new User(undefined, userName, selectedTeam);

                    storetoServer("UserServlet", user);


                })

                /**     * 11/19/17kl        */
                $(taskPage).find('#btnRetrieveTasks').click(function (evt) {
                    evt.preventDefault();
                    console.log('making ajax call');
                    retrieveTasksServer();
                });

                $(taskPage).find('#tblTasks tbody').on('click', 'tr', function (evt) {
                    $(evt.target).closest('td').siblings().andSelf().toggleClass('rowHighlight');
                });

                $(taskPage).find('#tblTasks tbody').on('click', '.deleteRow',
                    function (evt) {
                        storageEngine.delete('task', $(evt.target).data().taskId,
                            function () {
                                $(evt.target).parents('tr').remove();
                                taskCountChanged();
                            }, errorLogger);

                    }
                );

                $(taskPage).find('#tblTasks tbody').on('click', '.editRow',
                    function (evt) {
                        $(taskPage).find('#taskCreation').removeClass('not');
                        storageEngine.findById('task', $(evt.target).data().taskId, function (task) {
                            $(taskPage).find('form').fromObject(task);
                        }, errorLogger);
                    }
                );

                $(taskPage).find('#clearTask').click(function (evt) {
                    evt.preventDefault();
                    clearTask();
                });

                $(taskPage).find('#tblTasks tbody').on('click', '.completeRow', function (evt) {
                    storageEngine.findById('task', $(evt.target).data().taskId, function (task) {
                        task.complete = true;
                        storageEngine.save('task', task, function () {
                            tasksController.loadTasks();
                        }, errorLogger);
                    }, errorLogger);
                });

                $(taskPage).find('#saveTask').click(function (evt) {
                    evt.preventDefault();
                    if ($(taskPage).find('form').valid()) {
                        var task = $(taskPage).find('form').toObject();
                        task.user = JSON.parse(task.user);

                        storageEngine.save('task', task, function () {
                            $(taskPage).find('#tblTasks tbody').empty();
                            tasksController.loadTasks();
                            clearTask();
                            $(taskPage).find('#taskCreation').addClass('not');
                        }, errorLogger);
                    }
                });

                $(taskPage).find('#btnSaveTasks').on('click', function () {

                    storageEngine.findAll("task", storetoDB.bind(), errorLogger());

                    /**
                     * function to save data to the database
                     * passes data to a JSON object and uses ajax to create a post
                     * request to the server.
                     * @param data
                     */
                    function storetoDB(data) {

                        $.ajax({
                            "url": "TaskServlet",
                            "type": "post",
                            dataType: "json",
                            "data": {task: JSON.stringify(data)},
                            "success": onSuccess,
                            "fail": onFail
                        })

                        function onSuccess(data) {
                            serverTaskData = data;
                        }

                        function onFail() {
                            console.log("Failed to save to Database");
                        }

                    }

                })

                /* Priority Filter */
                $(taskPage).find('#priority-filter').on('change', function (evt) {
                    const priority = $(evt.target).val();

                    if (priority) {
                        storageEngine.findByProperty('task', 'priority', priority, function (tasks) {
                            loadTasksToTable(tasks);
                        }, errorLogger);
                    } else {
                        $(taskPage).find('#tblTasks tbody').empty();
                        tasksController.loadTasks();
                    }
                });

                /* User Filter */
                $(taskPage).find('#user-filter').on('change', function (evt) {
                    const userId = parseInt($(evt.target).val());

                    storageEngine.findAll('task', function (tasks) {
                        if (userId) {
                            tasks = tasks.filter(task => task.user.id === userId);
                        }

                        $(taskPage).find('#tblTasks tbody').empty();
                        loadTasksToTable(tasks);

                    }, errorLogger);
                });

                /**
                 *  Team Filter
                 *  Filters all the data by Team.
                 *  Uses the tasks object to look up for all users in the tasks who
                 *  belong to a team specified by the id passed in the value of the
                 *  DOM element.
                 *
                 *  */
                $(taskPage).find('#team-filter').on('change', function (evt) {
                    const teamId = parseInt($(evt.target).val());

                    storageEngine.findAll('task', function (tasks) {
                        if (teamId) {
                            tasks = tasks.filter(task => task.user.team.id === teamId);
                        }

                        $(taskPage).find('#tblTasks tbody').empty();
                        loadTasksToTable(tasks);

                    }, errorLogger);
                });


                initialised = true;
            }
        },
        /**
         * 111917kl
         * modification of the loadTasks method to load tasks retrieved from the server
         */
        loadServerTasks: function (tasks) {
            $(taskPage).find('#tblTasks tbody').empty();
            $.each(tasks, function (index, task) {
                if (!task.complete) {
                    task.complete = false;
                }
                $('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
                taskCountChanged();
                console.log('about to render table with server tasks');
                //renderTable(); --skip for now, this just sets style class for overdue tasks 111917kl
            });
        },
        loadTasks: function () {
            $(taskPage).find('#tblTasks tbody').empty();
            storageEngine.findAll('task', function (tasks) {
                tasks.sort(function (o1, o2) {
                    return Date.parse(o1.dueDate).compareTo(Date.parse(o2.dueDate));
                });
                $.each(tasks, function (index, task) {
                    if (!task.complete) {
                        task.complete = false;
                    }
                    $('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
                    taskCountChanged();
                    renderTable();
                });
            }, errorLogger);
        }
    }
}();
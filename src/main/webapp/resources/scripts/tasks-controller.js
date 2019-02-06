Skip to content

Pull requests
Issues
Marketplace
Explore
@StevenKatabalwa Sign out
2
0 0 vorleakchy/wap-task-list
Code  Issues 0  Pull requests 0  Projects 0  Wiki  Insights
wap-task-list/src/main/webapp/resources/scripts/tasks-controller.js
ed4cc6c  12 hours ago
@StevenKatabalwa StevenKatabalwa Updated client code for Tasks
@StevenKatabalwa @StevenKata

    249 lines (212 sloc)  8.42 KB
tasksController = function () {

    function errorLogger(errorCode, errorMessage) {
        console.log(errorCode + ':' + errorMessage);
    }

    var taskPage;
    var initialised = false;
    const SORT_TASK_ORDER_ASCEND = "ascend", SORT_TASK_ORDER_DESCEND = "descend";
    let toggleOrder = SORT_TASK_ORDER_ASCEND;

    let serverTaskData = null;

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
        serverTaskData = data;
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

    /***
     * filter tasks by team id
     *
     * Gets a list of all tasks from the server side sent through as JSON data,
     * Performs filter functions on it and returns only those whose users belong
     * to the Team specified by ID in the Params.
     * @param id
     */
    function filterByTeam(id) {

        serverTaskData = serverTaskData.filter((t) => t.user.teamID == id ? true : false).toArray();

    }

    /**
     * Performs sort operations on all tasks and returns the list as per the
     * sort requirement.
     * @param requirement
     */
    function sortTasksByTeam(requirement) {

        switch (requirement) {
            case SORT_TASK_ORDER_ASCEND:
                serverTaskData = sortData(serverTaskData);
                break;
            case  SORT_TASK_ORDER_DESCEND:
                serverTaskData = sortData(serverTaskData).reverse();
                break;
        }
    }

    //Toggle between the order of sorting using the specified requirement
    function toggleSortTasksByTeam() {

        if (toggleOrder == SORT_TASK_ORDER_ASCEND) {

            toggleOrder = SORT_TASK_ORDER_DESCEND
        } else {

            toggleOrder = SORT_TASK_ORDER_ASCEND;
        }

        sortTasksByTeam(toggleOrder);
    }

    /**
     * Sorts an array and returns a sorted version of itself.
     * @param data
     * @returns {array}
     */
    function sortData(data) {

        function swap(arr, a, b) {

            let c = arr[a];
            arr[a] = arr[b];
            arr[b] = c;

            return arr;
        }

        for (let i = 0; i < data.length; i++) {

            for (let k = i + 1; k < data.length; k++) {

                if (data[i] > data[k]) {

                    data = swap(data, i, k);
                }
            }
        }
        return data;
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
                });

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
                        storageEngine.save('task', task, function () {
                            $(taskPage).find('#tblTasks tbody').empty();
                            tasksController.loadTasks();
                            clearTask();
                            $(taskPage).find('#taskCreation').addClass('not');
                        }, errorLogger);
                    }
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
                    return Date.parse(o1.requiredBy).compareTo(Date.parse(o2.requiredBy));
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
}
();
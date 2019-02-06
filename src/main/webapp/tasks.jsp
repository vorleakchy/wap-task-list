<%--
  Created by IntelliJ IDEA.
  User: MAGIC ZONE
  Date: 2/5/2019
  Time: 4:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Task list</title>
    <link rel="stylesheet" type="text/css" href="resources/styles/tasks.css"	media="screen" />
    <script src="resources/scripts/jquery-2.0.3.js"></script>
    <script src="resources/scripts/jquery-tmpl.js"></script>
    <script src="resources/scripts/jquery.validate.js"></script>
    <script src="resources/scripts/jquery-serialization.js"></script>
    <script src="resources/scripts/tasks-controller.js"></script>
    <script src="resources/scripts/date.js"></script>
</head>
<body>
<header>
    <span>Task list</span>
</header>
<main  id="taskPage">
    <section id="taskCreation" class="not">
        <form id="taskForm">
            <input type="hidden" name="id"/>

            <div>
                <label>Task</label> <input type="text" required="required"
                                           name="task" class="large" placeholder="Breakfast at Tiffanys" maxlength="200"  />
            </div>
            <div>
                <label>Required by</label> <input type="date" required="required"
                                                  name="requiredBy" />
            </div>
            <div>
                <label>Category</label> <select name="category">
                <option value="Personal">Personal</option>
                <option value="Work">Work</option>
            </select>
            </div>
            <div>
                <label>Priority</label> <select name="priority">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
            </div>
            <nav>
                <a href="#" id="saveTask">Save task</a>    <!-- https://stackoverflow.com/questions/4855168/what-is-href-and-why-is-it-used -->
                <a href="#" id="clearTask">Clear task</a>
            </nav>
        </form>
    </section>
    <section>

    <section>
        <fieldset>
            <legend>Retrieve</legend>
            <div>
                <label>Priority</label> <select id="filter-priority">
                    <option value=""></option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
            </div>
        </fieldset>
    </section>

    </section>
    <section>
        <table id="tblTasks">
            <colgroup>
                <col width="10%">
                <col width="10%">
                <col width="10%">
                <col width="15%">
                <col width="15%">
                <col width="10%">
                <col width="30%">
            </colgroup>
            <thead>
            <tr>
                <th>User</th>
                <th>Team</th>
                <th>Name</th>
                <th>Due</th>
                <th>Category</th>
                <th>Priority</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <nav>
            <a href="#" id="btnAddTask">Add task</a>
            <a href="#" id="btnRetrieveTasks">Retrieve tasks from server</a>
        </nav>
    </section>
</main>
<footer>You have <span id="taskCount"></span> tasks</footer>

<div id="loader">
    <div class="inner-loader"></div>
    Loading...
</div>
</body>
<script>
    function initScreen() {
        $(document).ready(function() {
            const $loading = $('#loader');
			$loading.hide();

			$(document).ajaxStart(() => { $loading.show(); })
				.ajaxStop(()  => { $loading.hide(); });

            tasksController.init($('#taskPage'), function() {
                tasksController.loadTasks();
            });
        });
    }

    $.getScript( "resources/scripts/tasks-servlet.js" )
        .done(function( script, textStatus ) {
            initScreen();
        })
        .fail(function( jqxhr, settings, exception ) {
            console.log( 'Failed to load tasks-servlet.js' );
        });

    // if (window.indexedDB) {
    //     console.log("using indexedDB 111917kl");
    //     $.getScript( "resources/scripts/tasks-indexeddb.js" )
    //         .done(function( script, textStatus ) {
    //             initScreen();
    //         })
    //         .fail(function( jqxhr, settings, exception ) {
    //             console.log( 'Failed to load indexed db script' );
    //         });
    // } else if (window.localStorage) {
    //     console.log("using webstorage 111917kl");
    //     $.getScript( "resources/scripts/tasks-webstorage.js" )
    //         .done(function( script, textStatus ) {
    //             initScreen();
    //         })
    //         .fail(function( jqxhr, settings, exception ) {
    //             console.log( 'Failed to load web storage script' );
    //         });
    // }
</script>

<script id="taskRow" type="text/x-jQuery-tmpl">
<tr>
    <td {{if complete == true}}class="taskCompleted"{{/if}}>${task}</td>
    <td {{if complete == true}}class="taskCompleted"{{/if}}>${task}</td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>${task}</td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}><time datetime="${requiredBy}">${requiredBy}</time></td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>${category}</td>
	<td {{if complete == true}}class="taskCompleted"{{/if}}>${priority}</td>
	<td>
		<nav>
			{{if complete != true}}
				<a href="#" class="editRow" data-task-id="${id}">Edit</a>
				<a href="#" class="completeRow" data-task-id="${id}">Complete</a>
			{{/if}}
			<a href="#" class="deleteRow" data-task-id="${id}">Delete</a>
		</nav>
	</td>
</tr>
</script>


</html>
let authUserId;
let authUserName;
let authUserSurname;
let authUserWorkplace;
let authUserAge;
let authUserSalary;
let authUserUsername;
let authUserRoles;
//fetch GET
function fetchGetUsers() {
    fetch('http://localhost:8081/rest/allUsers')
        .then(response => response.json())
        .then(renderUsers);
}

function renderUsers(users) {
    let temp = '';
    users.forEach(function (u) {
        temp += '<td>' + u.id + '</td>';
        temp += '<td>' + u.name + '</td>';
        temp += '<td>' + u.surname + '</td>';
        temp += '<td>' + u.workplace + '</td>';
        temp += '<td>' + u.age + '</td>';
        temp += '<td>' + u.salary + '</td>';
        temp += '<td>' + u.username + '</td>';
        temp += '<td>' + u.roles.flatMap(r => r.role.replaceAll("ROLE_", " ")) + '</td>';
        temp += '<td><button type="button" id="openEditModal" class="btn btn-primary btn-sm" data-bs-toggle="modal" onclick="modalOperation(this);">' + 'Edit' + '</button></td>';
        temp += '<td><button type="button" id="openDeleteModal" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#deleteModalPopup" onclick="modalOperation(this);">' + 'Delete' + '</button></td></tr>';
    })
    document.getElementById("userTable").innerHTML = temp;
}

fetchGetUsers();

function fetchPostPutUser(method, url, roleVal) {
    let rolesJson;
    let roleAdmin;
    let roleUser;
    if (roleVal.length === 2) {
        rolesJson = [
            {
                "id": 1,
                "role": "ROLE_ADMIN",
                "usersSet": [],
                "authority": "ROLE_ADMIN"
            },
            {
                "id": 2,
                "role": "ROLE_USER",
                "usersSet": [],
                "authority": "ROLE_USER"
            }
        ];
    } else if (roleVal.length !== 2) {
        roleAdmin = [
            {
                "id": 1,
                "role": "ROLE_ADMIN",
                "usersSet": [],
                "authority": "ROLE_ADMIN"
            }
        ];
        roleUser = [
            {
                "id": 2,
                "role": "ROLE_USER",
                "usersSet": [],
                "authority": "ROLE_USER"
            }
        ];
        rolesJson = (roleVal[0] === "ROLE_ADMIN" ? roleAdmin : roleUser);
    }
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
        },
        body: JSON.stringify({
            id: event.target.id.value,
            name: event.target.name.value,
            surname: event.target.surname.value,
            workplace: event.target.workplace.value,
            age: event.target.age.value,
            salary: event.target.salary.value,
            username: event.target.username.value,
            password: event.target.password.value,
            roles: rolesJson,
        })
    })
        .then(response => response.json())
        .then(fetchGetUsers)
}

//fetch POST
const addNewUser = document.querySelector('#addNewUserForm');
addNewUser.addEventListener('submit', function (event) {
    event.preventDefault();
    let selectedRoles = document.querySelectorAll('#createRoles option:checked');
    const rolesVal = Array.from(selectedRoles).map(el => el.value);
    console.log(rolesVal);
    fetchPostPutUser('POST', 'http://localhost:8081/rest/addNew', rolesVal);
    document.getElementById("UsersTable-tab").click();
});


//fetch delete-edit
let editUserModalPopupWrap = null;
const modalOperation = (btn) => {
    const clickedButtonId = btn.id;

    const userTable = document.getElementById("listOfUsers");
    if (userTable) {
        for (let i = 0; i < userTable.rows.length; i++) {
            userTable.rows[i].onclick = function () {
                populateDeleteModal(this);
            }
            if (editUserModalPopupWrap !== null) {
                editUserModalPopupWrap.remove();
            }

            function populateDeleteModal(tableRow) {
                const userId = tableRow.childNodes[0].innerHTML;
                const userName = tableRow.childNodes[1].innerHTML;
                const userSurname = tableRow.childNodes[2].innerHTML;
                const userWorkplace = tableRow.childNodes[3].innerHTML;
                const userAge = tableRow.childNodes[4].innerHTML;
                const userSalary = tableRow.childNodes[5].innerHTML;
                const userUsername = tableRow.childNodes[6].innerHTML;
                const userPassword = tableRow.childNodes[7].innerHTML;

                if (clickedButtonId === "openDeleteModal") {
                    let temp = '';

                    temp += '<form id="deleteUser" method=\'DELETE\'>';
                    temp += '<fieldset>';
                    temp += '<div class="mb-3">';
                    temp += '<label for="idDel" class="col-form-label">' + 'id' + '</label>';
                    temp += '<input type="text" value="' + userId + '" class="form-control" id="idDel" name="id" readonly></div>';
                    temp += '<div class="mb-3">';
                    temp += '<label for="nameDel" class="col-form-label">' + 'First name' + '</label>';
                    temp += '<input type="text" value="' + userName + '" class="form-control" id="nameDel" name="name" readonly></div>';
                    temp += '<div class="mb-3">';
                    temp += '<label for="surnameDel" class="col-form-label">' + 'Last name' + '</label>';
                    temp += '<input type="text" value="' + userSurname + '" class="form-control" id="surnameDel" name="surname" readonly></div>';
                    temp += '<div class="mb-3">';
                    temp += '<label for="workplaceDel" class="col-form-label">' + 'Workplace' + '</label>';
                    temp += '<input type="text" value="' + userWorkplace + '" class="form-control" id="workplaceDel" name="workplace" readonly></div>';
                    temp += '<div class="mb-3">';
                    temp += '<label for="ageDel" class="col-form-label">' + 'Age' + '</label>';
                    temp += '<input type="number" value="' + userAge + '" class="form-control" name="age" readonly></div>';
                    temp += '<div class="mb-3">';
                    temp += '<label for="salaryDel" class="col-form-label">' + 'Salary' + '</label>';
                    temp += '<input type="number" value="' + userSalary + '" class="form-control" name="salary" readonly></div>';
                    temp += '<div class="mb-3">';
                    temp += '<label for="usernameDel" class="col-form-label">' + 'Username' + '</label>';
                    temp += '<input type="text" value="' + userUsername + '" class="form-control" id="usernameDel" name="username" readonly></div>';
                    temp += '<div class="mb-3">';
                    temp += '<label for="passwordDel" class="col-form-label">' + 'Password' + '</label>';
                    temp += '<input type="password" value="' + userPassword + '" class="form-control" id="passwordDel" name="password" readonly></div>';
                    temp += '<div class="mb-3">';
                    temp += '<label>' + 'Role' + '</label>';
                    temp += '<select class="form-control" size="2" multiple aria-label="multiple select example" id="deleteRoles" aria-multiselectable="true">';
                    temp += '<option value="ROLE_ADMIN">' + 'ADMIN' + '</option>';
                    temp += '<option value="ROLE_USER">' + 'USER' + '</option></select></div></fieldset></form> ';
                    temp += '<button type="button" id="deleteModalCloseButton" class="btn btn-secondary" data-bs-dismiss="modal">' + 'Close' + '</button>';
                    temp += '<input type="submit" value="Delete" id="deleteSubmitBtn" class="btn btn-primary" form="deleteUser"/>';

                    document.getElementById("deleteModalBody").innerHTML = temp;
                    document.getElementById('deleteSubmitBtn').addEventListener('click', async function (e) {
                        e.preventDefault();
                        fetch('http://localhost:8081/rest/deleteUser/' + userId, {
                            method: 'DELETE'
                        }).then(fetchGetUsers);
                        document.getElementById('deleteModalCloseButton').click();
                    });
                } else if (clickedButtonId === "openEditModal") {
                    editUserModalPopupWrap = document.createElement('div');
                    editUserModalPopupWrap.innerHTML =
                        '<form  method=\'PUT\' id="editUserModalForm">' +
                        '<div class="modal" id="editUserModalPopup" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">' +
                        '                                                <div class="modal-dialog">' +
                        '                                                    <div class="modal-content">' +
                        '                                                        <div class="modal-header">' +
                        '                                                            <h5 class="modal- title" id="editModalLabel">Edit user</h5>' +
                        '                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div>' +
                        '                                                        <div class="modal-body">' +
                        '                                                            <div class="mb-3">' +
                        '                                                                <label for="idE" class="col-form-label">id</label>' +
                        '                                                                <input type="text" class="form-control" value="' + userId + '" id="idE" name="id" readonly></div>' +
                        '                                                            <div class="mb-3">' +
                        '                                                                <label for="nameE" class="col-form-label">First name</label>' +
                        '                                                                <input type="text" class="form-control" value="' + userName + '" id="nameE" name="name"></div>' +
                        '                                                            <div class="mb-3">' +
                        '                                                                <label for="surnameE" class="col-form-label">Last name</label>' +
                        '                                                                <input type="text" class="form-control" value="' + userSurname + '" id="surnameE" name="surname"></div>' +
                        '                                                            <div class="mb-3">' +
                        '                                                                <label for="workplaceE" class="col-form-label">Workplace</label>' +
                        '                                                                <input type="text" class="form-control" value="' + userWorkplace + '" id="workplaceE" name="workplace"></div>' +
                        '                                                            <div class="mb-3">' +
                        '                                                                <label for="ageE" class="col-form-label">Age</label>' +
                        '                                                                <input type="number" class="form-control" value="' + userAge + '" id="ageE" name="age"></div>' +
                        '                                                            <div class="mb-3">' +
                        '                                                                <label for="salaryE" class="col-form-label">Salary</label>' +
                        '                                                                <input type="number" class="form-control" value="' + userSalary + '" id="salaryE" name="salary"></div>' +
                        '                                                            <div class="mb-3">' +
                        '                                                                <label for="usernameE" class="col-form-label">Username</label>' +
                        '                                                                <input type="text" class="form-control" value="' + userUsername + '" id="usernameE" name="username"></div>' +
                        '                                                            <div class="mb-3">' +
                        '                                                                <label for="passwordE" class="col-form-label">Password</label>' +
                        '                                                                <input type="password" class="form-control" value="' + userPassword + '" id="passwordE" name="password"></div>' +
                        '                                                            <div class="mb-3">' +
                        '                                                                <label>Role </label>' +
                        '                                                                <select class="form-control" size="2" multiple aria-label="multiple select example" id="editRoles" name="editRoles" aria-multiselectable="true">' +
                        '                                                                    <option id="1" value="ROLE_ADMIN">ADMIN</option>' +
                        '                                                                    <option id="2" value="ROLE_USER">USER</option></select></div>' +
                        '                                                            <button type="button" id="editModalCloseBtn" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>' +
                        '                                                                <button type="submit" id="editSubmitBtn" class="btn btn-primary" form="editUserModalForm">Submit</button></div></div></div></div></form>';

                    document.body.append(editUserModalPopupWrap);
                    let modal = new bootstrap.Modal(document.getElementById('editUserModalPopup'));
                    modal.show();

                    const editModal = document.querySelector("#editUserModalForm");
                    editModal.addEventListener('submit', function (event) {
                        event.preventDefault();
                        let selectedRolesForEdit = document.querySelectorAll('#editRoles option:checked');
                        const roleValue = Array.from(selectedRolesForEdit).map(el => el.value);

                        fetchPostPutUser('PUT', 'http://localhost:8081/rest/edit/', roleValue);
                        fetchGetUsers();
                        document.getElementById('editModalCloseBtn').click();
                    })
                }
            }
        }
    }
}


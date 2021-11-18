
async function loadJson(url) {
    var response = await fetch(url)
    var j = await response.json()
    document.getElementById('id').value = j.id
    document.getElementById('surname').value = j.surname

}
loadJson('https://localhost:8081/admin/allUsers')


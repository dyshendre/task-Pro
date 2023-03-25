let taskForm = document.getElementById("task-form");
taskForm.addEventListener("submit", addTask);

function addTask(event){
    event.preventDefault(event);
   
    let taskType=taskForm.taskType.value;
    let taskName=taskForm.taskName.value;
    let assignee=taskForm.assignee.value;
    let status=taskForm.status.value;
    
    let tr=document.createElement("tr");

    let td1=document.createElement("td");
    td1.innerText=taskType;

    let td2=document.createElement("td");
    td2.innerText=taskName;

    let td3=document.createElement("td");
    td3.innerText=assignee;

    let td4=document.createElement("td");
    td4.innerText=status;

    tr.append(td1,td2,td3,td4);
    document.getElementById("sprint-tbody").append(tr);
}
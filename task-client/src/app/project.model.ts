//
// export interface Task  {
//   id:number;
//   title:string;
//   status:TaskStatus;
//   projectID:number;
//   assigneeID:number;
//   tagsID:number[];
//   dueDate:string;
// }

export interface Project {
  id:number;
  name:string;
  description:string;
  managerID:number
}

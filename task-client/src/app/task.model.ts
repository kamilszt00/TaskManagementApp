export type TaskStatus = "TODO" | "IN_PROGRESS" | "COMPLETED";


export interface Task  {
  id:number;
  title:string;
  status:TaskStatus;
  projectID:number;
  assigneeID:number;
  tagsID:number[];
  dueDate:string;
}

export interface CreateTaskRequest {
  title:string;
  status:TaskStatus;
  dueDate:string;
  projectID:number;
  assigneeID:number;
  tagsID:number[];

}









# Task Management App: Remaining Work

Part 1 is what the frontend needs to work for all three roles. Part 2 is everything else, grouped by the original priority.

# Part 1: Essential for a functional frontend

### Manager frontend
- [ ] "New task" leftover: reset the old error message when the form opens
- [ ] Task details: delete (with a confirmation dialog)
- [ ] Task details: reassign (dropdown of project members)
- [ ] Task details: edit
- [ ] Edit project: name and description

### Admin frontend
- [ ] Users page: list users (admin only, with a Users link in the toolbar)
- [ ] Users page: create user (username, email, password, role)
- [ ] Users page: delete user
- [ ] Create project: name, description, manager dropdown (`GET /user?role=MANAGER`)
- [ ] Delete project, with confirmation
- [ ] Merge projects, with confirmation

### Needed on the phone
- [ ] Add `/projects` and `/projects/**`, and the new `/users` page, to `permitAll()` and to `WebController`'s `@GetMapping`

---

# Part 2: The rest

## A. Must have for the deadline

### Backend
- [ ] `createProject`: set `createdAt` on the server (column is `nullable = false`, nothing sets it, so creation fails with 400 unless the client sends it)
- [ ] `deleteProject`: decide delete vs. merge (currently `projectId` to reassign is required, so there is no plain delete; make it optional or add a separate merge endpoint)
- [ ] `deleteProject`: check that moved incomplete tasks are not deleted along with the rest (`findAllByProject` runs after the reassignment)
- [ ] `deleteProject`: return type is `ResponseEntity<ProjectResponse>` but it returns 204 with no body
- [ ] Fix `updateUser`: modify the existing entity instead of creating a new one (currently saves the password unencoded and drops task/comment relations)
- [ ] `deleteUser`: handle users who manage projects, are project members, or have tasks (currently fails with a generic 400)
- [ ] `createUser`: return a clear error for a duplicate email instead of the generic 400
- [ ] `GET /user/{id}`: restrict access (any logged-in user can read any user's email and task titles)
- [ ] `createProject`: remove the duplicate manager lookup (`getReferenceById` + `findById`)

### Running on the phone
- [ ] Full test on the phone through `deploy.sh` and a tunnel, with all three roles
- [ ] Demo setup:
  - [ ] Test accounts for employee, manager, and admin
  - [ ] Plan for running Spring, the database, and the tunnel on the laptop during the presentation

## B. Should have

- [ ] Handler for `MethodArgumentTypeMismatchException`, returning 400
- [ ] Visual polish of the project list card
- [ ] Visual polish of the project details card
- [ ] Task details card: remove duplicate rows (Status, Task Title)
- [ ] Format `dueDate` with the `date` pipe
- [ ] Readable statuses ("In progress" instead of `IN_PROGRESS`)
- [ ] Error and success messages as snackbars
- [ ] Footer at the bottom of the screen
- [ ] Check every page in the phone-sized device toolbar
- [ ] Remove debug leftovers ("Logged in" text, `console.log` calls)
- [ ] Commit and push both projects to Git

## C. For the final thesis version

- [ ] Ask the instructor which requirements from the requirements table are mandatory
- [ ] Frontend unit tests (services, guards)
- [ ] UI tests, if required
- [ ] Documentation:
  - [ ] Run instructions
  - [ ] Architecture description, including the MVVM mapping
  - [ ] What each role can see and do
  - [ ] Security decisions: token storage in `localStorage`, stateless JWT logout, 401 vs 403
- [ ] Show names instead of ids in details (project name, assignee username)

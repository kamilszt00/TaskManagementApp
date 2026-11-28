# Session notes

Where we stopped and how to continue. Update at the end of each session.
The task list itself is in `task-management-checklist.md` (Part 1 = essential for the frontend).

## How we work
- The student writes the code. Claude is the mentor: explains, gives hints, reviews. No ready solutions unless asked.
- One small step at a time. Name the exact file and line, show the pattern on an unrelated example, compare with Java/Spring (TypeScript syntax is new).
- Claude edits code only on request (so far: spec files, styling, the checklist, `onDelete()` and the task details error box).
- "check" means: read the diff, compile, run the tests, then say what is right and what to fix.

## Last session: 2026-11-26

### Finished
- Leftovers from 2026-11-24: error reset in `task-create.ts`, unused `MatCardModule` removed from `task-list.ts` (commit `48beee4`)
- **Delete task**, tested in the browser with all roles (code commit `ff3831c`):
  - `TaskService.deleteTask(id)`: reads `projectID` from `selectedTask()` into a `const` first, returns early if it is missing, then `DELETE /task/{id}`, navigates to `/projects/{projectId}` and clears `selectedTask`
  - `TaskDetails.onDelete()`: `ConfirmDialog` → `afterClosed()` → `deleteTask(this.taskId)` (written by Claude on request)
  - "Delete" button on the task details page inside `@if (authService.canManage())`
  - Red `.task-error` box above the buttons for `taskService.errorMessage()`, and the message is reset in the constructor (done by Claude on request)

### Things the student learned this session
- Guard clause in TS: `const x = a()?.b; if (!x) return;` (same as an early `return` on `null` in Java). `?.` gives `undefined` instead of throwing.
- A service method like `loadTask` returns `void`; its result comes later through the signal (`selectedTask()`), not as a return value.
- A click handler needs a parameter only when the button is inside `@for` (like `onRemoveMember(user)`); on a details page use the component field (`this.taskId`).
- If a service sets `errorMessage`, the page must also show it, or failures are silent.

### Do this first next time
- Commit the `Docs` changes (checklist and this file) if they are still uncommitted.

### Then continue with
**Reassign** on the task details page.
- Backend: `PATCH /task/{id}/reassign?userId=...` (a query parameter, not a body; `@RequestParam` in `TaskController.java:58`). Returns the updated `TaskResponse`.
- Members for the dropdown: `projectService.loadMembers(projectId)` + `projectService.members()`, as in `task-create`. The project id comes from `selectedTask()`, which is loaded asynchronously, so it is not available in the constructor right away.
- Backend refuses completed tasks and the same user, so hide or disable it for completed tasks and show the error box.
- Visible for `canManage()` only.

Order after that: edit task, edit project, then admin: list users, create user, delete user, create project, delete/merge project, then the phone task.

## Conventions
- Forms: main action is `type="submit" mat-flat-button`; every other button is `type="button" mat-stroked-button` (a button without a type submits the form)
- Buttons at least 44px high, errors in the red box, dialog look set in `styles.css`
- Path id: `paramMap`. After the `?`: `queryParamMap`
- `roleGuard` must be called in the routes: `roleGuard(['ADMIN'])`. Without parentheses it compiles but lets everyone in
- An empty `mat-select` does not open, so use `<mat-option disabled>` inside `@empty`

## Backend limits to remember
- Create/edit task: at least one tag id is required and no endpoint lists tags; `dueDate` cannot be edited
- Reassign: the backend refuses completed tasks and the same user, and does not check that the user is a project member
- Edit project: a multi-line description is rejected, so use a single-line input
- Users list: one request per role, returns only id and username
- Create user with a duplicate email, or delete a user who is still in use: generic 400, so show our own message
- Create project: the client must send `createdAt` (until fixed on the server)
- Delete project: `projectId` is required, so it is really a merge; unfinished tasks move, completed ones are deleted
- Assigning a non-manager to a project returns 500 (`InvalidUserRoleException` has no handler)

## Known test failures (old, not caused by this work)
`task-card.spec.ts` and `project-details.spec.ts`: "No provider found for ActivatedRoute". They need router and HTTP providers like in `task-details.spec.ts`. Last run: 11 passed, 2 failed.

# Session notes

Where we stopped and how to continue. Update at the end of each session.
The task list itself is in `task-management-checklist.md` (Part 1 = essential for the frontend).

## How we work
- The student writes the code. Claude is the mentor: explains, gives hints, reviews. No ready solutions unless asked.
- One small step at a time. Name the exact file and line, show the pattern on an unrelated example, compare with Java/Spring (TypeScript syntax is new).
- Claude edits code only on request (so far: spec files, styling, the checklist).
- "check" means: read the diff, compile, run the tests, then say what is right and what to fix.

## Last session: 2026-11-24

### Finished
- Role helpers `canManage` and `isAdmin` in `AuthService`
- `roleGuard([...])` in `role-guard.ts`, used on `/tasks` (ADMIN) and `/tasks/new` (ADMIN, MANAGER)
- Toolbar links per role
- Links and redirects that led to `/tasks` now go to the project or to `/projects`
- `ConfirmDialog`, used for "Remove member" on the project page
- New task from the project page: `?projectId=` in the address, assignee dropdown, Cancel back to the project, error message
- Styling of the new pieces, done by Claude

### Do this first next time
1. `task-create.ts`: reset `taskService.errorMessage` in the constructor (an old error from another page can show in the new form; `createTaskRequest` now resets it after a successful create, but that does not cover opening the form), and move the constructor above `onSubmit()`
2. `task-list.ts`: remove the unused `MatCardModule`
3. Commit. The code from this session is committed (`2a90e48`); when this note was written, only the checklist and this file in `Docs` were not

### Then continue with
**Delete task** on the task details page. Reuses `ConfirmDialog` (see `onRemoveMember` in `project-details.ts`). Backend: `DELETE /task/{id}`, manager of the project or admin. Afterwards go back to the task's project.

Order after that: reassign, edit task, edit project, then admin: list users, create user, delete user, create project, delete/merge project, then the phone task.

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

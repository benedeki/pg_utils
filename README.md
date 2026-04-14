# PG Utils

PG Utils is a collection of utility functions (and supporting structures) that simplify some tasks and offer abilities 
that are often needed when working with Postgres databases. 

*Table of contents*
- [Installation](#installation)
- [Usage](#usage)
- [Documentation](#documentation)
  - [Schema `pgarray`](#schema-pgarray)
    - [is_unique](#is_unique)
  - [Schema `pgutils`](#schema-pgutils)
    - [global_id](#global_id)
    - [lock_mutex](#lock_mutex)
    - [log_to_console](#log_to_console)
- [Contributing to PG Utils](#contributing-to-pg-utils)
  - [Did you find a bug?](#did-you-find-a-bug)
  - [Do you want to request a new feature?](#do-you-want-to-request-a-new-feature)
  - [Do you want to implement a new feature, fix a bug or improved the documentation?](#do-you-want-to-implement-a-new-feature-fix-a-bug-or-improved-the-documentation) 
- [Tests](#tests)
- [Notes](#notes)

## Installation

For now use command

```bash
psql "postgres://user:passowrd@host:port/dbname" -f install.psql
```

or if you use .pgpass file for authentication, you can omit the password and use:

```bash
psql -h host -p port -U user -d dbname -f install.psql
```

or integrate with your deployment/migration tool of choice,

or deploy it manually executing each file found in the database folder.

## Usage

All functions are available in the `pgutils` and `pgarray` schemas. And they are assigned to the `public` role, so 
they are available to all users by default. 

Objects not having `public` role assigned  are intended to be user only by 
the library itself and are not intended to be used directly by users of the library. They are not documented in the 
documentation section below.

## Documentation

### Schema `pgarray`

#### is_unique

Checks if the array contains only unique values

| Parameter           | Type              | Description                                                                            |
|---------------------|-------------------|----------------------------------------------------------------------------------------|
| `i_array`           | array of any type | The array of any type to check                                                         |
| `i_nulls_distinct`  | BOOLEAN           | if TRUE, NULL values are treated as distinct, otherwise they are considered duplicates |

| Returns      |                                                                                                            |
|--------------|------------------------------------------------------------------------------------------------------------|
| BOOLEAN      | `TRUE` if the array contains only unique values,<br/>`FALSE` if the array has at least one duplicate value |

### Schema `pgutils`

#### global_id

Generates a unique ID, with the proper setup unique over all your servers

| Returns |                           |
|---------|---------------------------|
| BIGINT  | The unique 64-bit integer |

### lock_mutex

The goal of this function is to implement concurrent operation execution - operation is given in the input parameter.

Call this function at the beginning of an operation (usually a wider UPDATE/INSERT) that you want to prevent concurrent
execution of, and the release will happen automatically when the transaction finishes or rolls back.

| Parameter    | Type | Description                                                                                                                |
|--------------|------|----------------------------------------------------------------------------------------------------------------------------|
| i_mutex_name | TEXT | Name of the operation that will perform the lock. The name is the key for locking, different names won't block each other. |

### log_to_console

Prints out a message to the console, optionally prefixed by current timestamp. Useful when a progress report is handy
during long operations.

| Parameter       | Type    | Default | Description                                                                       |
|-----------------|---------|---------|-----------------------------------------------------------------------------------|
| i_log_message   | TEXT    | -       | Message to write into the console.                                                | 
| i_add_timestamp | BOOLEAN | `TRUE` | If TRUE, message is prefixed by a timestamp, otherwise only the message is output. | 

## Tests

Tests will be added in the future using the [Balta](https://github.com/AbsaOSS/balta) library.

## Contributing to PG Utils

### Did you find a bug?

* **Ensure the bug has not already been reported** by searching the **[GitHub Issues](https://github.com/benedeki/PGUtils/issues)**.
* If you are unable to find an open issue describing the problem, use the **Bug report** template to open a new one.

### Do you want to request a new feature?

* **Ensure the feature has not already been requested** by searching the **[GitHub Issues](https://github.com/benedeki/PGUtils/issues)**.
* If you are unable to find the feature request, create a new one.

### Do you want to implement a new feature, fix a bug or improved the documentation?

* Check [_Issues_](https://github.com/benedeki/PGUtils/issues) logs for the feature/bug. Check if someone isn't already working on it.
* If the issue or bug doesn't exist, please write it up first (see above).
* Assign the issue to yourself, so others know that someone is working on it.
* Fork the repository. (unless you have access to the main repository, in that case you can create a branch directly in the main repository)
* Follow the naming conventions for branches  - best is to use the automation workflow that creates the branch name based on the issue number and title. Type `/create-branch` in the comment section of the issue and the branch will be created for you.
  * The branch will be cut from the main and will have the issue number and title in the name, prefixed by the type of the work (_feature_, _bugfix_, _docs_ or _infra_).
* Code away. Ask away. 
  * Commit messages should start with a reference to the GitHub Issue and provide a brief description in the imperative mood:
    * **"#42 Answer the ultimate question"**
  * Don't forget to write tests for your work.
* After finishing everything, push to your forked repo/branch and open a Pull Request to the project main branch:
  * Pull Request titles should start with the Github Issue number:
    * **"42 Life, the universe and everything"**
  * Ensure the Pull Request description clearly describes the solution.
  * Add a section **Release notes** to the PR description:
    * The release notes will be utilized by the automation to generate the release notes for the release, using [generate-release-notes action](https://github.com/AbsaOSS/generate-release-notes). 
    * Add a line for each change that should be included in the release notes, prefixed by a bullet point. The line should be concise and clear, describing the change in a way that is suitable for release notes.
    * If the change doesn't need to be included in the release notes (like a documentation change), add a label `no RN` to the PR
  * Connect the PR to the _Issue_

**Thanks!**

## Notes

Originally it was intended to name the package and main schema _pg_utils_, and the owning role _pg_utils_owner_. 
But the prefix `pg_` is reserved for Postgres internal objects, so we had to rename the objets. Just omitting the
underscore seems to be the best option.

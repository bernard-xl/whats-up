# What's up
A Reddit clone (prepared for the Coding challenge, see it in action [here][0])

### Design Overview
This application is designed with [CQRS][1] principle. The application is seperated into one 'write-side' and multiple 'read-side', 
and an event system is there to propagate changes of entity from 'write-side' to all 'read-side'.

The 'write-side' serves as source of truth, and 'read-side's are denormalized view that are optimized for query. 
Such design makes the application more extensible, scalable, and adaptable to changes.

### Data Structure
The 'write-side' uses concurrent hash map (a.k.a `ConcurrentHashMap`) that allows multiple users to operate concurrently with minimal lock-contention.

The 'read-side' use red-black tree map (a.k.a `TreeMap`) to store data in different order according to the corresponding ranking algorithm, 
the integrity of 'read-side' data is protected by read-write lock (I assumes this application has much more read than write).

### Topics Sorting
The topics are sorted in 4 ways: `Hot`, `Top`, `New`, `Controversy`. The ranking algorithms are the same as those used in [Reddit][2].

### Authentication
The user doesn't need to register any account To sign in, just tell the application what's your name, no password is required.

## User Interface
This is a very basic single page application (SPA) with some flaws, but it is perfectly usable.

Here are known flaws:
* Sign-in doesn't persist across refresh.
* No router, so it can't make use of the browser's history mechanism.
* Tab switching (i.e. switch between `Hot`, `Top`, and etc.) is asynchronous, 
  clicking them too fast under high latency network may result in non-disastrous but unexpected result.

These flaws are not fixed currently because I believe they are not the main points of assessment. 
I need to deliver the application as soon as possible.
  

[0]: https://bernard-xl-whatsup.herokuapp.com/
[1]: https://docs.microsoft.com/en-us/azure/architecture/patterns/cqrs
[2]: https://github.com/reddit/reddit/blob/master/r2/r2/lib/db/_sorts.pyx

rugbliss
========

A command line app to control your [Blackout Rugby](http://www.blackoutrugby.com/) team.

Usage
=====

You must provide a configuration file at the following location:

    ~/.rugbliss/config.clj

It will contain the configuration needed by the application to connect to your account, e.g.:

    {:api-keys {:key "cscaASX23423dcaSC"
                :iv  "asASCcsa3243sacSA"}}

You can obtain the above information by vising your [account page](http://www.blackoutrugby.com/game/me.account.php#page=account).

Alpha warning
=============

The code is still incredibly immature, use at your own risk

License
=======

All the code is released under the MIT License.

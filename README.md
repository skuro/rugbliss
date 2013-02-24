rugbliss
========

A command line app to control your [Blackout Rugby](http://www.blackoutrugby.com/) team, written in [clojure](http://www.clojure.org).

Usage
=====

You must provide a configuration file at the following location:

    ~/.rugbliss/config.clj

It will contain the configuration needed by the application to connect to your account, e.g.:

    {:api-keys {:key "cscaASX23423dcaSC"
                :iv  "asASCcsa3243sacSA"}}

You can obtain the above information by vising your [account page](http://www.blackoutrugby.com/game/me.account.php#page=account).

To run, go to the folder where you checked out the sources, then run:

    lein trampoline run

Developers
==========

`rugbliss` is designed to be easily extended. Commands can be created by using the `defcommand` macro as follows:

    (use 'rugbliss.commands)

    (defcommand mycommand
      "Some awesome piece of documentation"
      [one two]
      (println (str one two"))

Alpha warning
=============

The code is still incredibly immature, use at your own risk

License
=======

All the code is released under the MIT License.

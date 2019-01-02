#!/usr/bin/env bash
ps -e | grep -i chromedriver | awk {'print '} | xargs kill

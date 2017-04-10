#!/bin/csh -f

if($1 == "-h") then
    echo "loadTable <filename> <comment>"
    exit
endif

# get text file name from common line argument
set filename = $1
sed '1d' $filename > tmpfile;  #remove first line with column names

#get comment from command line
set comment = "$2"

# get ccdb settings
source /group/clas12/environment.csh 

# add table to the DB
ccdb -c mysql://clas12writer:geom3try@clasdb/clas12 add /calibration/dc/time_to_distance/tvsx_devel_v2 -v dc_test1 tmpfile \#$comment

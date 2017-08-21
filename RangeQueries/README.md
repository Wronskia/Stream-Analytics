# Description

Here, we present the design of a structure
based on Bloom filters that can answer range containment queries, i.e., given a range [l,r] of 
IP addresses from your network, the structure will return true if the packets seen so far
contain at least one IP address x, with l <= x <=r, with a false positive probability at most pr.

It uses a technique of adaptave bloom filters which minimizes the queries to the data.

# Author 

Yassine Benyahia

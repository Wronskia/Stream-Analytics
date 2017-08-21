# Description

- Given a network of at most 400.000 IP addresses. we keep an approximate count of the number of packets sent by any
IP address from the network with memory constraint.  The sketch used is bloom filters with pr1, which is given by the user at construction
time (false positive probability)

- Any frequency query for an item that appears in the stream will return an estimate
for the frequency with a maximum absolute error ε*L, with a probability pr2. L is the
number of items seen so far. ε and pr2 will be given by the user at construction time.

# Author 

Yassine Benyahia

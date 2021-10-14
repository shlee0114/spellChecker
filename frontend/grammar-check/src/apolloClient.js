import ApolloClient from "apollo-client";
import { InMemoryCache } from "apollo-cache-inmemory";
import { createHttpLink } from "apollo-link-http";

const url = new ApolloClient({
    link: createHttpLink({uri : "http://localhost:8089/graphql/"}),
    cache: new InMemoryCache()
  })

export default url

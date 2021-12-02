import ApolloClient from "apollo-client";
import { InMemoryCache } from "apollo-cache-inmemory";
import { createHttpLink } from "apollo-link-http";
import { graphqlServerIp } from "./static/setting";

const url = new ApolloClient({
    link: createHttpLink({uri : graphqlServerIp}),
    cache: new InMemoryCache()
  })

export default url

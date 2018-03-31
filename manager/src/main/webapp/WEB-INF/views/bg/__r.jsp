<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach var="node" items="${treeList}" varStatus="vs">
	<c:choose>
		<c:when test="${level == 0}">
			<ul id="tree" class="treeview" style="margin-left: 20px;">
				<c:choose>
					<c:when test="${fn:length(node.children.list) > 0}">
						<li class="collapsable"><div
								class="hitarea expandable-hitarea"></div> <span
							type="${node.type }" nodeId="${node.id }"
							nodeText="${node.text }" nodePId="${node.parentId }" class=""
							style="font-weight: bolder;">${node.text }</span>
							 <c:set
								var="level" value="${level + 1}" scope="request" /> <c:set
								var="treeList" value="${node.children.list}" scope="request" />
							<ul style="display: none;">
								<c:import url="__r.jsp" />
							</ul></li>
					</c:when>
					<c:otherwise>
						<li><span type="${node.type }" nodeId="${node.id }"
							nodeText="${node.text }" nodePId="${node.parentId }" class="">${node.text
								}</span> </li>
					</c:otherwise>
				</c:choose>
			</ul>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${fn:length(node.children.list) > 0}">
					<li class="expandable"><div class="hitarea expandable-hitarea"></div>
						<span type="${node.type }" nodeId="${node.id }"
						nodeText="${node.text }" nodePId="${node.parentId }" class="">${node.text
							}</span>
						<c:set var="level"
							value="${level + 1}" scope="request" /> <c:set var="treeList"
							value="${node.children.list}" scope="request" />
						<ul style="display: none;">
							<c:import url="__r.jsp" />
						</ul></li>
				</c:when>
				<c:otherwise>
					<li><span type="${node.type }" nodeId="${node.id }"
						nodeText="${node.text }" nodePId="${node.parentId }" class="">${node.text
							}</span></li>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	<c:set var="index" value="${index + 1}" scope="request" />
</c:forEach>
<c:set var="level" value="${level - 1}" scope="request" />

